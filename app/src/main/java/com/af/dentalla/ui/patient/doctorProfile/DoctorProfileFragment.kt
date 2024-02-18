package com.af.dentalla.ui.patient.doctorProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentDoctorProfileBinding
import com.af.dentalla.ui.patient.homeScreen.PatientHomeFragmentDirections
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.loadImage
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.showToastShort
import com.af.dentalla.utilities.visible
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
    }

    private fun setUpBackButton() {
        binding.back.setOnClickListener {
            findNavController().safeNavigate(DoctorProfileFragmentDirections.actionDoctorProfileFragmentToHomeFragment())
        }
    }

    private fun setProfileDetail() {
        doctorProfileViewModel.doctorProfile.observe(viewLifecycleOwner) { profileState ->
            when (profileState) {
                is ScreenState.Loading -> binding.progress.visible()
                is ScreenState.Error -> {
                    binding.progress.gone()
//                    requireView().showToastShort(profileState.message)
                }

                is ScreenState.Success -> {
                    binding.progress.gone()
                    val profile = profileState.uiData
                    binding.apply {
//                        doctorImg.loadImage(profile.doctorPhoto)
                        textViewDoctorName.text = profile.doctorName
                        textViewDoctorSpeciality.text =
                            profile.specialty.toString() ///speciality is int
                        textViewPhoneNumber.text = profile.phoneNumber
                        textViewAbout.text = profile.about
                    }
                    //availability not finished
                }
            }
        }
    }


}