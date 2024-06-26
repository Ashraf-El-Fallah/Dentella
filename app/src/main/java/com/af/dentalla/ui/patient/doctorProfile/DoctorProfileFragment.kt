package com.af.dentalla.ui.patient.doctorProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentDoctorProfileBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.getSpecialtyName
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.loadImage
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorProfileFragment : Fragment() {
    private lateinit var binding: FragmentDoctorProfileBinding
    private val doctorProfileViewModel: DoctorProfileViewModel by viewModels()
    private val args: DoctorProfileFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        doctorProfileViewModel.getDoctorProfile(args.doctorId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProfileDetail()
        setUpBackButton()
//        setUpAvailableSpecialities()
    }

//    private fun setUpAvailableSpecialities() {
//        val staticSpecialities = listOf(
//            "Cardiology",
//            "Neurology",
//            "Orthopedics",
//            "Pediatrics",
//            "Radiology"
//        )
//        val adapter = AvailableSpecialitiesAdapter(staticSpecialities)
//        binding.recyclerViewDoctorSpecialities.adapter = adapter
//        binding.recyclerViewDoctorSpecialities.layoutManager =
//            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//    }

    private fun setUpBackButton() {
        binding.back.root.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setProfileDetail() {
        doctorProfileViewModel.doctorProfile.observe(viewLifecycleOwner) { profileState ->
            when (profileState) {
                is ScreenState.Loading -> binding.progress.progress.visible()
                is ScreenState.Error -> {
                    binding.progress.progress.gone()
                    Toast.makeText(requireContext(), profileState.message, Toast.LENGTH_LONG).show()
                }


                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    val profile = profileState.uiData
                    binding.apply {
                        doctorImg.loadImage(profile.doctorPhoto)
                        textViewDoctorName.text = profile.doctorName
                        textViewDoctorSpeciality.text = getSpecialtyName(context, profile.specialty)
                        textViewPhoneNumber.text = profile.phoneNumber
                        textViewAbout.text = profile.about

                        val availableDates = profile.doctorAvailability.flatMap {
                            it.availableDates ?: emptyList()
                        }
                        rvDate.apply {
                            adapter = AvailableDatesAdapter(
                                availableDates
                            ).apply {
                                submitList(availableDates)
                            }
                            layoutManager =
                                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        }
                    }
                }
            }
        }
    }
}

