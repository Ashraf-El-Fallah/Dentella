package com.af.dentalla.ui.patient.homeScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.databinding.BaseHomeScreenBinding
import com.af.dentalla.databinding.FragmentPatientHomeBinding
import com.af.dentalla.ui.patient.DoctorsCardsAdapter
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeFragment : Fragment() {
    private lateinit var binding: FragmentPatientHomeBinding
    private lateinit var baseHomeBinding: BaseHomeScreenBinding

    private val patientHomeViewModel: PatientHomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientHomeBinding.inflate(inflater, container, false)
        baseHomeBinding = BaseHomeScreenBinding.bind(binding.topBackground.root)
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
        baseHomeBinding.editTextSearchHome.setOnClickListener {
            navigateToSearchScreen()
        }
        baseHomeBinding.editTextSearchHome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    navigateToSearchScreen()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
                        adapter = DoctorsCardsAdapter { doctorCardId ->
                            navigateToDoctorProfile(doctorCardId)
                        }.apply { submitList(it.uiData) }
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