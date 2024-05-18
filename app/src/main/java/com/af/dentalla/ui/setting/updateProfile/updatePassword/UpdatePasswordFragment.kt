package com.af.dentalla.ui.setting.updateProfile.updatePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
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
            if (!isPasswordValid(newPassword) || (newPassword == oldPassword)) {
                Snackbar.make(
                    requireView(),
                    R.string.error_when_changing_password,
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
                        R.string.error_when_changing_old_password,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ScreenState.Success -> {
                    binding.progressBar.root.gone()
                    Toast.makeText(
                        requireContext(),
                        R.string.change_password_successfully,
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