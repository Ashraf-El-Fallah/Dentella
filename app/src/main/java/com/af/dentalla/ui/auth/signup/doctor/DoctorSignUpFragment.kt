package com.af.dentalla.ui.auth.signup.doctor

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.common.Constants
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.requests.SignUpPatient
import com.af.dentalla.databinding.FragmentDoctorSignUpBinding
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorSignUpFragment : Fragment() {

    private val viewModel: DoctorSignUpViewModel by viewModels()
    private lateinit var binding: FragmentDoctorSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorSignUpBinding.inflate(inflater, container, false)
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
        val id = binding.editTextId.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        viewModel.isSignUpValidate(userName, email, phone, id, password, confirmPassword)

        viewModel.signUpDoctorState.observe(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is NetWorkResponseState.Loading -> {
                    binding.progress.visible()
                    binding.buttonSignUp.isEnabled = false
                }

                is NetWorkResponseState.Success -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    findNavController().navigate(DoctorSignUpFragmentDirections.actionDoctorSignUpFragmentToHomeFragment())
                }

                is NetWorkResponseState.Error -> {
                    binding.progress.gone()
                    binding.buttonSignUp.isEnabled = true
                    requireView().showToastShort(R.string.check_username_pass.toString())
                }
            }
        }
    }


//    private fun setOnClicks() {
//        val navigateToLogin =
//            CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginAccountFragment()
//        binding.signUp.setOnClickListener {
//            view?.findNavController()
//                ?.navigate(navigateToLogin)
//        }
//
//        binding.signIn.setOnClickListener {
//            view?.findNavController()
//                ?.navigate(navigateToLogin)
//        }
//
//
//    }


    //    private fun setUpSignUpButton() {
//        binding.signUp.setOnClickListener { signUpLogic() }
//    }
//
//    private fun signUpLogic() {
////        val fullName = binding.fullName.text.toString()
//        val email = binding.etEmail.text.toString()
//        val phone = binding.phone.text.toString()
//        val password = binding.confirmPassword.text.toString()
//
//        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            requireView().showToastShort("Please Enter valid email")
//            return
//        }
////        if (fullName.isEmpty()) {
////            requireView().showToastShort("Please Enter your name")
////            return
////        }
//        if (phone.isEmpty()) {
//            requireView().showToastShort("Please Enter your phone number")
//            return
//        }
//        if (password.isEmpty() || password.length < 8) {
//            requireView().showToastShort("Please Enter valid password")
//            return
//        }
//
//        val signUpPatient = SignUpPatient(email, phone, password)
//        viewModel.signUp(signUpPatient)
//

}