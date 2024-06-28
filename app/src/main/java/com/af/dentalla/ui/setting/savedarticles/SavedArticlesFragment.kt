package com.af.dentalla.ui.setting.savedarticles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentSavedArticlesBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedArticlesFragment : Fragment() {
    private lateinit var binding: FragmentSavedArticlesBinding
    private val savedArticlesViewModel: SavedArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesObserver()
    }

    private fun articlesObserver() {
        savedArticlesViewModel.savedArticles.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is ScreenState.Loading -> binding.progress.progress.visible()
                is ScreenState.Success -> {
                    binding.progress.root.gone()

                    val articlesList = screenState.uiData
                    binding.recyclerViewSavedArticles.apply {
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        adapter = SavedArticleAdapter().apply {
                            submitList(articlesList)
                        }
                    }
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                    Toast.makeText(
                        context,
                        screenState.message ?: "Unknown error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}