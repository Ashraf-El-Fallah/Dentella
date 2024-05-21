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
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private val articlesViewModel: ArticlesViewModel by viewModels()

    // TODO: make it lazy
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
        addArticleObserver()
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

    private fun addArticleObserver() {
        articlesViewModel.addArticleState.observe(viewLifecycleOwner) { addArticleState ->
            when (addArticleState) {
                is ScreenState.Loading -> {
                    binding.progress.root.visible()
                }

                // TODO: you can combine those together
                is ScreenState.Success ,
                is ScreenState.Error -> {
                    binding.progress.root.gone()
                }

                /*is ScreenState.Success -> {
                    binding.progress.root.gone()
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                }*/
            }
        }
    }

    private fun articlesObserver() {
        articlesViewModel.articles.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> binding.progress.progress.visible()
                is ScreenState.Success -> {
                    binding.progress.root.gone()

                    binding.rvArticles.apply {
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        adapter = ArticlesAdapter().apply {
                            submitList(it.uiData)
                        }
                    }
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}