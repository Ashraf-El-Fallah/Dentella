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
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.visible
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

    private fun navigateToSearchFragment() {
        binding.editTextSearchHome.setOnClickListener {
            findNavController().safeNavigate(PatientHomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }


    private fun allCardsObserver() {
        patientHomeViewModel.allCards.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Loading -> {
                    binding.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.gone()
                    binding.rvDoctors.apply {
                        adapter = DoctorsCardsAdapter { doctorCardId ->
                            navigateToDoctorProfile(doctorCardId)
                        }
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }
                    (binding.rvDoctors.adapter as DoctorsCardsAdapter).submitList(it.uiData)
                }

                is ScreenState.Error -> {
                    binding.progress.gone()
//                    Toast.makeText(
//                        context,
//                        it.message,
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }
        }
    }


    private fun setUpSpecialitiesRecyclerView() {
        val specialtiesList = listOf(
            Speciality(R.drawable.cleaning, 0, "Cleaning"),
            Speciality(R.drawable.dental_filling, 1, "Filling"),
            Speciality(R.drawable.crown, 2, "Crowns"),
            Speciality(R.drawable.dental_implant, 3, "Implants"),
            Speciality(R.drawable.extraction, 4, "Extraction"),
            Speciality(R.drawable.denture, 5, "Dentures"),
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