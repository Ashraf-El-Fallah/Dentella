package com.af.dentalla.ui.setting.updateProfile.updatePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentUpdatePasswordBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastLong
import com.af.dentalla.utils.showToastShort
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
                        context?.showToastLong(getString(R.string.want_to_login_again))
                    } else {
                        val errorMessage =
                            changePasswordState.errorMessageCode?.let { getString(it) }
                                ?: getString(R.string.network_error)
                        context?.showToastLong(errorMessage)
                    }
                }

                is ScreenState.Success -> {
                    binding.progressBar.root.gone()
                    context?.showToastShort(getString(R.string.change_password_successfully))

                }
            }
        }
    }

    private fun navigateToEditProfiledScreen() {
        binding.back.root.setOnClickListener {
            val action =
                UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToEditProfileFragment()
            findNavController().safeNavigate(action)
        }
    }
}