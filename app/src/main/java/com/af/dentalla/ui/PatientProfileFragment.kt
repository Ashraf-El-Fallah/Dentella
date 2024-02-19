package com.af.dentalla.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentPatientProfileBinding
import com.af.dentalla.utilities.safeNavigate
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PatientProfileFragment : Fragment() {
    private lateinit var binding: FragmentPatientProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToHome()
        goToEditProfile()
    }

    private fun backToHome() {
        binding.back.setOnClickListener {
            findNavController().safeNavigate(PatientProfileFragmentDirections.actionPatientProfileFragmentToHomeFragment())
        }
    }

    private fun goToEditProfile() {
        binding.cardViewEditProfile.setOnClickListener {
//            findNavController().safeNavigate(PatientProfileFragmentDirections.actionPatientProfileFragmentToEditProfileFragment())
            val action =
                PatientProfileFragmentDirections.actionPatientProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }
}