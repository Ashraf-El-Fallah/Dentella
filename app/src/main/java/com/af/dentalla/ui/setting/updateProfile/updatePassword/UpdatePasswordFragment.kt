package com.af.dentalla.ui.setting.updateProfile.updatePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentUpdatePasswordBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentUpdatePasswordBinding
    private val updatePasswordViewModel: UpdatePasswordViewModel by viewModels()

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
            updatePasswordViewModel.changeDoctorPassword(
                oldPassword = oldPassword,
                newPassword = newPassword
            )
        }
    }


    private fun changeDoctorPasswordObserver() {
        updatePasswordViewModel.updateUserPasswordState.observe(viewLifecycleOwner) { changePasswordState ->
            when (changePasswordState) {
                is ScreenState.Loading -> {
                    binding.progressBar.root.visible()
                }

                is ScreenState.Error -> {
                    binding.progressBar.root.gone()

                    if (changePasswordState.statusCode == 401) {
                        Toast.makeText(
                            requireContext(),
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val errorMessage =
                            changePasswordState.errorMessageCode?.let { getString(it) }
                                ?: getString(R.string.network_error)
                        Toast.makeText(
                            requireContext(),
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
        binding.back.root.setOnClickListener {
            val action =
                UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }
}