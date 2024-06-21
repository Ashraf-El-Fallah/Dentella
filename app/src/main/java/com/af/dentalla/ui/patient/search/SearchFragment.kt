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
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSearchBinding
import com.af.dentalla.ui.patient.DoctorsCardsAdapter
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService

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
        lifecycleScope.launch {
            AccountManager.accountType = dataStorePreferencesService.getAccountType()
        }
        navigateToHomeScreen()
    }

    private fun navigateToHomeScreen() {
        binding.back.root.setOnClickListener {
            val action = if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
                SearchFragmentDirections.actionSearchFragmentToDoctorHomeFragment()
            } else {
                SearchFragmentDirections.actionSearchFragmentToHomeFragment()
            }
            findNavController().safeNavigate(action)
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
                    binding.progress.progress.visible()

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    binding.recyclerViewSearchedCards.apply {
                        adapter = DoctorsCardsAdapter(
                            onItemClick = { doctorCardId ->
                                navigateToDoctorProfile(doctorCardId)
                            },
                            onInfoClick = { doctorCardId ->
                                navigateToDoctorProfile(doctorCardId)
                            }
                        ).apply { submitList(it.uiData) }
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }
                }

                is ScreenState.Error -> {
                    binding.progress.progress.gone()
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