package com.af.dentalla.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        showBottomSheet()
    }

    private fun goToEditProfile() {
        binding.cardViewEditProfile.setOnClickListener {
            val action =
                SettingFragmentDirections.actionPatientProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun showBottomSheet() {
        binding.cardViewChangeLanguage.setOnClickListener {
            val bottomSheetFragment = ChangeLanguageBottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

}