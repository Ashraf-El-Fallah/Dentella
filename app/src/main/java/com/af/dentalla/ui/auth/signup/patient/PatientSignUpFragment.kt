package com.af.dentalla.ui.auth.signup.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.databinding.FragmentDoctorSignUpBinding
import com.af.dentalla.databinding.FragmentPatientSignUpBinding
import com.af.dentalla.ui.auth.signup.doctor.DoctorSignUpViewModel
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.showToastShort
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatientSignUpFragment : Fragment() {

    private val viewModel: PatientSignUpViewModel by viewModels()
    private lateinit var binding: FragmentPatientSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpObserver()
    }

    private fun signUpObserver() {
        val userName = binding.editTextUserName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        lifecycleScope.launch {
            viewModel.signUp(userName, email, phone, password, confirmPassword)
        }

        viewModel.signUpPatientState.observe(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is NetWorkResponseState.Loading -> {
                    binding.progress.visible()
                    binding.buttonSignUp.isEnabled = false
                }

                is NetWorkResponseState.Success -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                }

                is NetWorkResponseState.Error -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    requireView().showToastShort(R.string.check_username_pass.toString())
                }
            }

        }

    }


}