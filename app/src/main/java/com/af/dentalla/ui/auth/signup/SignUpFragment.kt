package com.af.dentalla.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentSignUpBinding
import com.af.dentalla.ui.base.BaseFragment
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
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
        navigateToLoginScreen()
    }

    private fun navigateToLoginScreen() {
        binding.textViewSignIn.setOnClickListener {
            findNavController().safeNavigate(SignUpFragmentDirections.actionSignUpFragmentToLoginAccountFragment())
        }
    }

    private fun passUserDataToViewModel() {
        binding.buttonSignUp.setOnClickListener {
            val userName = binding.editTextUserName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            val id = binding.editTextId.text.toString()
                .takeIf { accountType == AccountManager.AccountType.DOCTOR }

            viewModel.signUpUserLogic(
                accountType.toString(),
                userName,
                email,
                phone,
                password,
                confirmPassword,
                id
            )
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
                    binding.progress.progress.visible()
                    binding.buttonSignUp.isEnabled = false
                }

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    findNavController().safeNavigate(SignUpFragmentDirections.actionSignUpFragmentToLoginAccountFragment())
                    Toast.makeText(
                        requireContext(),
                        R.string.sign_up_successfully,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                is ScreenState.Error -> {
                    binding.progress.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    Toast.makeText(requireContext(), signUpState.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}