package com.af.dentalla.ui.setting.savedarticles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentSavedArticlesBinding
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.showToastLong
import com.af.dentalla.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedArticlesFragment : Fragment() {
    private lateinit var binding: FragmentSavedArticlesBinding
    private val savedArticlesViewModel: SavedArticlesViewModel by viewModels()
    private lateinit var savedArticleAdapter: SavedArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        articlesObserver()
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerViewSavedArticles)
        }
    }

    private fun setupRecyclerView() {
        savedArticleAdapter = SavedArticleAdapter()
        binding.recyclerViewSavedArticles.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = savedArticleAdapter
        }
    }

    private fun articlesObserver() {
        savedArticlesViewModel.savedArticles.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is ScreenState.Loading -> binding.progress.progress.visible()
                is ScreenState.Success -> {
                    binding.progress.root.gone()
                    val articlesList = screenState.uiData
                    savedArticleAdapter.submitList(articlesList)
                    checkIfListIsEmpty(articlesList)
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                    context?.showToastLong(screenState.message.toString())
                }
            }
        }
    }

    private fun checkIfListIsEmpty(articlesList: List<ArticleSavedEntity>) {
        if (articlesList.isEmpty()) {
            binding.textViewEmptyList.visible()
        } else {
            binding.textViewEmptyList.gone()
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = savedArticleAdapter.currentList[position]
            val mutableList = savedArticleAdapter.currentList.toMutableList()
            mutableList.removeAt(position)
            savedArticleAdapter.submitList(mutableList)
            savedArticlesViewModel.deleteSavedArticle(article)

            Snackbar.make(requireView(), R.string.delete_article, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}
