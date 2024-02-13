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
import com.af.dentalla.utilities.AccountManager
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.ValidationUtils
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.showToastShort
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding
    private val accountType = AccountManager.accountType
//    private var id: String = ""


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
        setUpSignUpButton()
    }

    private fun setUpSignUpButton() {
        binding.buttonSignUp.setOnClickListener { validateUserDataValid() }
    }

    private fun isPhoneNumberValid(phoneNumber: String) {
        if (ValidationUtils.isPhoneNumberNotValid(phoneNumber)) {
            requireView().showToastShort("This phone number is not valid")
            return
        }
    }

    private fun isPasswordAndConformationPasswordValid(
        password: String,
        confirmPassword: String
    ) {
        if (ValidationUtils.isPasswordAndConfirmationNotEqual(password, confirmPassword)) {
            requireView().showToastShort("This password is not valid or not equal confirmation password")
            return
        }
    }

    private fun validateUserDataValid() {
        val userName = binding.editTextUserName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()



        binding.buttonSignUp.setOnClickListener {
            if (accountType == AccountManager.AccountType.PATIENT) {
                isUserNameValid(userName)
                isEmailValid(email)
                isPhoneNumberValid(phone)
                isPasswordAndConformationPasswordValid(password, confirmPassword)
                viewModel.signUpPatientLogic(SignUpPatient(userName, email, password, phone))
            } else if (accountType == AccountManager.AccountType.DOCTOR) {
                val id = binding.editTextId.text.toString()
                isIdValid(binding.editTextId.text.toString())
                viewModel.signUpDoctorLogic(SignUpDoctor(userName, email, password, phone, id))
            }
        }
//        binding.buttonSignUp.setOnClickListener {
//            if (binding.editTextId.visibility == View.VISIBLE)
//                if (accountType == AccountManager.AccountType.DOCTOR) {
//                    val id = binding.editTextId.text.toString()
//                    isIdValid(binding.editTextId.text.toString())
//                    viewModel.signUpDoctorLogic(SignUpDoctor(userName, email, password, phone, id))
//                }
//        }

    }

    private fun isIdValid(id: String) {
        if (ValidationUtils.isIdNotValid(id)) {
            requireView().showToastShort("This id is not valid")
            return
        }
    }

    private fun selectTheUserType() {
        if (accountType == AccountManager.AccountType.PATIENT) {
            binding.textInputId.gone()
//            val patientData = validateUserDataValid()
//            viewModel.signUpPatientLogic(patientData)
//            signUpObserver()
        }
        if (accountType == AccountManager.AccountType.DOCTOR) {
            binding.textInputId.visible()
//            val doctorData = validateUserDataValid()
//            viewModel.signUpDoctorLogic(doctorData)
//            signUpObserver()
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
                    requireView().showToastShort("Please Check your personal information")
                }
            }
        }
    }
}