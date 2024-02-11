package com.af.dentalla.ui.patient.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentHomeBinding
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val patientHomeViewModel: PatientHomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsObserver()
    }


    private fun cardsObserver() {
        patientHomeViewModel.allCards.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Loading -> {
                    binding.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.gone()
                    binding.rvDoctors.adapter = AllDoctorsCardsAdapter { doctorCard ->
                        navigateToDoctorProfile(doctorCard.cardId)
                    }
                    (binding.rvDoctors.adapter as AllDoctorsCardsAdapter).submitList(it.uiData)
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

    private fun navigateToDoctorProfile(doctorId: Int) {
        val action =
            PatientHomeFragmentDirections.actionHomeFragmentToDoctorProfileFragment(doctorId)
        findNavController().navigate(action)
    }
}