package com.af.dentalla.ui.patient.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentArticlesBinding
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private val articlesViewModel: PatientArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesObserver()
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
                        adapter = PatientArticlesAdapter().apply {
                            submitList(it.uiData)
                        }
                    }
//                    binding.rvArticles.adapter = PatientArticlesAdapter()
//                    (binding.rvArticles.adapter as PatientArticlesAdapter).submitList(it.uiData)
//                    val adapter = binding.rvArticles.adapter
//                    if (adapter is PatientArticlesAdapter) {
//                        adapter.submitList(it.uiData)
//                    }
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