package com.af.dentalla.ui.patient.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentHomeBinding
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun cardsObserver() {
        homeViewModel.allCards.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Loading -> {
                    binding.progress.visible()
                }

                is ScreenState.Success -> {

                }

                is ScreenState.Error -> {
                    binding.progress.gone()
                }
            }
        }
    }


}