package com.af.dentalla.ui.patient.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentSearchBinding
import com.af.dentalla.ui.patient.DoctorsCardsAdapter
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.showToastShort
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAboutCards()
        searchedCardsObserver()
        navigateToHomeScreen()
    }

    private fun navigateToHomeScreen() {
        binding.back.root.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun searchAboutCards() {
        var searchJob: Job? = null
        binding.editTextSearch.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(300)
                searchViewModel.getCardsBySearch(searchQuery.toString())
            }
        }
    }

    private fun searchedCardsObserver() {
        searchViewModel.searchedCards.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading ->
                    binding.progress.visible()

                is ScreenState.Success -> {
                    binding.progress.gone()
                    binding.recyclerViewSearchedCards.apply {
                        adapter =
                            DoctorsCardsAdapter { doctorCardId ->
                                navigateToDoctorProfile(doctorCardId)
                            }.apply { submitList(it.uiData) }
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }
                }

                is ScreenState.Error -> {
                    binding.progress.gone()
                }
            }
        }
    }

    private fun navigateToDoctorProfile(id: Int) {
        findNavController().safeNavigate(
            SearchFragmentDirections.actionSearchFragmentToDoctorProfileFragment(
                id
            )
        )
    }
}