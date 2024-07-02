package com.af.dentalla.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.data.local.datastore.DataStorePreferencesService
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.databinding.FragmentArticlesBinding
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.showToastLong
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private val articlesViewModel: ArticlesViewModel by viewModels()

    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            AccountManager.accountType = dataStorePreferencesService.getAccountType()
            showAddArticlesButtonForDoctors()
        }
        articlesObserver()
        showDialogToWriteArticle()
        addArticleObserver()
        observeSaveArticleToast()
    }

    private fun observeSaveArticleToast() {
        articlesViewModel.saveArticleToast.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                context?.showToastShort(getString(R.string.save_article_successfully))
            }
        }
    }

    private fun showAddArticlesButtonForDoctors() {
        if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
            binding.buttonAddArticle.visible()
        } else {
            binding.buttonAddArticle.gone()
        }
    }

    private fun showDialogToWriteArticle() {
        binding.buttonAddArticle.setOnClickListener {
            AddArticleDialog(requireContext(),
                object : AddArticleDialogListener {
                    override fun onArticleAdded(article: Article) {
                        articlesViewModel.addArticle(article)
                    }
                }).show()
        }
    }

    private fun addArticleObserver() {
        articlesViewModel.addArticleState.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { addArticleState ->
                when (addArticleState) {
                    is ScreenState.Loading -> {
                        binding.progress.root.visible()
                    }

                    is ScreenState.Success -> {
                        binding.progress.root.gone()
                        context?.showToastShort(getString(R.string.send_article_successfully))
                    }

                    is ScreenState.Error -> {
                        binding.progress.root.gone()
                        if (addArticleState.statusCode == 401) {
                            context?.showToastLong(getString(R.string.want_to_login_again))
                        } else {
                            context?.showToastShort(getString(R.string.network_error))
                        }
                    }
                }
            }
        }
    }

    private fun articlesObserver() {
        articlesViewModel.articles.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { articlesState ->
                when (articlesState) {
                    is ScreenState.Loading -> binding.progress.progress.visible()
                    is ScreenState.Success -> {
                        binding.progress.root.gone()

                        binding.rvArticles.apply {
                            adapter = ArticlesAdapter { articlesEntity ->
                                articlesViewModel.saveArticleToDataBase(articlesEntity)
                            }.apply {
                                submitList(articlesState.uiData)
                            }
                            layoutManager =
                                LinearLayoutManager(
                                    activity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                        }
                    }

                    is ScreenState.Error -> {
                        binding.progress.root.gone()
                        context?.showToastShort(getString(R.string.network_error))
                    }
                }
            }
        }
    }
}