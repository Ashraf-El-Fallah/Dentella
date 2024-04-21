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
import com.af.dentalla.ui.patient.doctorProfile.DoctorProfileFragmentDirections
import com.af.dentalla.ui.patient.homeScreen.PatientHomeFragmentDirections
import com.af.dentalla.ui.patient.homeScreen.Speciality
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.visible
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
                    binding.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.gone()
//                    binding.speciality.text=Speciality.
                    binding.rvDoctorsSpecialities.apply {
                        adapter = DoctorsCardsAdapter { doctorCardId ->
                            navigateToDoctorProfile(doctorCardId)
                        }
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }
                    (binding.rvDoctorsSpecialities.adapter as DoctorsCardsAdapter).submitList(it.uiData)
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

    private fun navigateToDoctorProfile(doctorId: Int) {
        val action =
            DoctorsSpecialitiesFragmentDirections.actionDoctorsSpecialitiesFragmentToDoctorProfileFragment(
                doctorId
            )
        findNavController().navigate(action)
    }


}