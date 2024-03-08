package com.af.dentalla.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToEditProfile()
    }

//    private fun backToHome() {
//        binding.back.setOnClickListener {
//            findNavController().safeNavigate(SettingFragmentDirections.actionPatientProfileFragmentToHomeFragment())
//        }
//    }

    private fun goToEditProfile() {
        binding.cardViewEditProfile.setOnClickListener {
//            findNavController().safeNavigate(PatientProfileFragmentDirections.actionPatientProfileFragmentToEditProfileFragment())
            val action =
                SettingFragmentDirections.actionPatientProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }
}