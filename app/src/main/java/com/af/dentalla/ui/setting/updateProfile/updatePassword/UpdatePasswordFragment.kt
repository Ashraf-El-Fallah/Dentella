package com.af.dentalla.ui.setting.updateProfile.updatePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.databinding.FragmentUpdatePasswordBinding
import com.af.dentalla.ui.base.BaseFragment
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentUpdatePasswordBinding
    private val changePasswordViewModel: UpdatePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeDoctorPasswordObserver()
        passPasswordsToViewModel()
        navigateToEditProfiledScreen()
    }

    private fun passPasswordsToViewModel() {
        binding.buttonConfirm.setOnClickListener {
            val newPassword = binding.editTextNewPassword.text.toString()
            val oldPassword = binding.editTextOldPassword.text.toString()
            if (isPasswordNotValid(newPassword) || (newPassword == oldPassword)) {
                Snackbar.make(
                    requireView(),
                    "Error when changing password .. your new password is less than 8 digits or you add the same password in new password field",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                val doctorPassword =
                    DoctorPassword(newPassword = newPassword, oldPassword = oldPassword)
                changePasswordViewModel.changeDoctorPassword(doctorPassword)
            }
        }
    }

    private fun changeDoctorPasswordObserver() {
        changePasswordViewModel.changeDoctorPasswordState.observe(viewLifecycleOwner) { changePasswordState ->
            when (changePasswordState) {
                is ScreenState.Loading -> {
                    binding.progressBar.root.visible()
                }

                is ScreenState.Error -> {
                    binding.progressBar.root.gone()
                    Toast.makeText(
                        requireContext(),
                        "Error when changing password .. please add your old password correctly",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ScreenState.Success -> {
                    binding.progressBar.root.gone()
                    Toast.makeText(
                        requireContext(),
                        "You have changed password successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun navigateToEditProfiledScreen() {
        binding.back.setOnClickListener {
            val action =
                UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }
}