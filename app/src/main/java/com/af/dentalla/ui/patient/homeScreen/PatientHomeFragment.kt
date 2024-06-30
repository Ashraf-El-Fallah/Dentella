package com.af.dentalla.ui.patient.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentPatientHomeBinding
import com.af.dentalla.ui.patient.DoctorsCardsAdapter
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastLong
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeFragment : Fragment() {
    private lateinit var binding: FragmentPatientHomeBinding
    private val patientHomeViewModel: PatientHomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allCardsObserver()
        setUpSpecialitiesRecyclerView()
        navigateToSearchFragment()
    }


    private fun navigateToSearchScreen() {
        findNavController().safeNavigate(PatientHomeFragmentDirections.actionHomeFragmentToSearchFragment())
    }

    private fun navigateToSearchFragment() {
        binding.topBackground.editTextSearchHome.setOnClickListener {
            navigateToSearchScreen()
        }
    }


    private fun allCardsObserver() {
        patientHomeViewModel.allCards.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Loading -> {
                    binding.progress.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    binding.rvDoctors.apply {
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
                    context?.showToastLong(getString(R.string.server_error))
                }
            }
        }
    }


    private fun setUpSpecialitiesRecyclerView() {
        val specialtiesList = listOf(
            Speciality(R.drawable.cleaning, 0, getString(R.string.cleaning)),
            Speciality(R.drawable.dental_filling, 1, getString(R.string.filling)),
            Speciality(R.drawable.crown, 2, getString(R.string.crowns)),
            Speciality(R.drawable.dental_implant, 3, getString(R.string.implants)),
            Speciality(R.drawable.extraction, 4, getString(R.string.extraction)),
            Speciality(R.drawable.denture, 5, getString(R.string.orthodontic)),
        )

        val adapter = SpecialtyAdapter(specialtiesList) { specialityId ->
            val action =
                PatientHomeFragmentDirections.actionHomeFragmentToDoctorsSpecialitiesFragment(
                    specialityId
                )
            findNavController().navigate(action)
        }
        binding.recyclerViewSpeciality.adapter = adapter
        binding.recyclerViewSpeciality.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun navigateToDoctorProfile(doctorId: Int) {
        val action =
            PatientHomeFragmentDirections.actionHomeFragmentToDoctorProfileFragment(doctorId)
        findNavController().navigate(action)
    }
}