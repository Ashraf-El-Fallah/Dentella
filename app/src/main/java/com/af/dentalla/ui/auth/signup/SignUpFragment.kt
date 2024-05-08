package com.af.dentalla.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.remote.requests.SignUpDoctor
import com.af.dentalla.data.remote.requests.SignUpPatient
import com.af.dentalla.databinding.FragmentSignUpBinding
import com.af.dentalla.ui.base.BaseFragment
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.ValidationUtils
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding
    private val accountType = AccountManager.accountType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpObserver()
        selectTheUserType()
        passUserDataToViewModel()
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        if (ValidationUtils.isPhoneNumberNotValid(phoneNumber)) {
            requireView().showToastShort("This phone number is not valid")
            return false
        }
        return true
    }

    private fun isPasswordAndConformationPasswordValid(
        password: String,
        confirmPassword: String
    ): Boolean {
        if (ValidationUtils.isPasswordAndConfirmationNotEqual(password, confirmPassword)) {
            requireView().showToastShort("This password is not valid or not equal confirmation password")
            return false
        }
        return true
    }

    private fun isIdValid(id: String): Boolean {
        if (ValidationUtils.isIdNotValid(id)) {
            requireView().showToastShort("This id is not valid")
            return false
        }
        return true
    }

    private fun passUserDataToViewModel() {
        binding.buttonSignUp.setOnClickListener {
            val userName = binding.editTextUserName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            if (accountType == AccountManager.AccountType.PATIENT) {
                if (isUserNameValid(userName) && isEmailValid(email) && isPhoneNumberValid(phone) && isPasswordAndConformationPasswordValid(
                        password,
                        confirmPassword
                    )
                ) {
                    val signUpPatient = SignUpPatient(
                        username = userName,
                        email = email,
                        password = password,
                        phoneNumber = phone
                    )
                    viewModel.signUpUserLogic(signUpPatient)
                }
            } else if (accountType == AccountManager.AccountType.DOCTOR) {
                val id = binding.editTextId.text.toString()
                if (isIdValid(id) && isUserNameValid(userName) && isEmailValid(email) && isPhoneNumberValid(
                        phone
                    ) && isPasswordAndConformationPasswordValid(
                        password,
                        confirmPassword
                    )
                ) {
                    val signUpDoctor = SignUpDoctor(
                        username = userName,
                        id = id,
                        email = email,
                        password = password,
                        phoneNumber = phone
                    )
                    viewModel.signUpUserLogic(signUpDoctor)
                }
            }
        }
    }


    private fun selectTheUserType() {
        if (accountType == AccountManager.AccountType.PATIENT) {
            binding.textInputId.gone()
        }
        if (accountType == AccountManager.AccountType.DOCTOR) {
            binding.textInputId.visible()
        }
    }


    private fun signUpObserver() {
        viewModel.signUpDoctorState.observe(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is ScreenState.Loading -> {
                    binding.progress.visible()
                    binding.buttonSignUp.isEnabled = false
                }

                is ScreenState.Success -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    findNavController().safeNavigate(SignUpFragmentDirections.actionSignUpFragmentToLoginAccountFragment())
                }

                is ScreenState.Error -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
//                    requireView().showToastShort("Can't connect to data base")
                }
            }
        }
    }
}