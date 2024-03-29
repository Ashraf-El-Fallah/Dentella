package com.af.dentalla.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.databinding.FragmentArticlesBinding
import com.af.dentalla.utilities.AccountManager
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private val articlesViewModel: ArticlesViewModel by viewModels()
    private val accountType = AccountManager.accountType


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        showAddArticlesButtonForDoctors()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesObserver()
        showDialogToWriteArticle()
    }

    private fun showAddArticlesButtonForDoctors() {
        if (accountType == AccountManager.AccountType.DOCTOR) {
            binding.buttonAddArticle.visible()
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

    private fun articlesObserver() {
        articlesViewModel.articles.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> binding.progress.visible()
                is ScreenState.Success -> {
                    binding.progress.gone()

                    binding.rvArticles.apply {
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        adapter = ArticlesAdapter().apply {
                            submitList(it.uiData)
                        }
                    }
                }

                is ScreenState.Error -> {
                    binding.progress.gone()
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}