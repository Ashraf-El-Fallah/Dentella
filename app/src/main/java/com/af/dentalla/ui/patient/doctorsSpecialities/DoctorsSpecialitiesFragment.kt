package com.af.dentalla.ui.patient.doctorsSpecialities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentDoctorsSpecialityBinding
import com.af.dentalla.ui.patient.DoctorsCardsAdapter
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorsSpecialitiesFragment : Fragment() {
    private lateinit var binding: FragmentDoctorsSpecialityBinding
    private val args: DoctorsSpecialitiesFragmentArgs by navArgs()
    private val doctorsSpecialityViewModel: DoctorsSpecialitiesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorsSpecialityBinding.inflate(inflater, container, false)
        doctorsSpecialityViewModel.getDoctorsSpecialityCards(args.specialityId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        specialityCardsObserver()
        backToLastScreen()
    }

    private fun backToLastScreen() {
        binding.back.root.setOnClickListener {
            findNavController().safeNavigate(DoctorsSpecialitiesFragmentDirections.actionDoctorsSpecialitiesFragmentToHomeFragment())
        }
    }

    private fun specialityCardsObserver() {
        doctorsSpecialityViewModel.specialityCards.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Loading -> {
                    binding.progress.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
//                    binding.speciality.text=Speciality.
                    binding.rvDoctorsSpecialities.apply {
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

    private fun navigateToDoctorProfile(doctorId: Int) {
        val action =
            DoctorsSpecialitiesFragmentDirections.actionDoctorsSpecialitiesFragmentToDoctorProfileFragment(
                doctorId
            )
        findNavController().navigate(action)
    }


}