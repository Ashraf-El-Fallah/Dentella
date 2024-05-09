package com.af.dentalla.ui.setting.updateProfile.updatePassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentUpdatePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentUpdatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToEditProfiledScreen()
    }

    private fun navigateToEditProfiledScreen() {
        binding.back.setOnClickListener {
            val action =
                UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }
}