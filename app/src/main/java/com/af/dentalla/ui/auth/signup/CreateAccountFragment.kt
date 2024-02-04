package com.af.dentalla.ui.auth.signup

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.common.Constants
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.SignUpUser
import com.af.dentalla.databinding.FragmentCreateAccountBinding
import com.af.dentalla.ui.auth.login.LoginFragmentDirections
import com.af.dentalla.ui.auth.login.LoginViewModel
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private val viewModel: CreateAccountViewModel by viewModels()
    private lateinit var binding: FragmentCreateAccountBinding
    private lateinit var sharedPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSignUpButton()
    }

    private fun setUpSignUpButton() {
        binding.signUp.setOnClickListener { signUpLogic() }
    }

    private fun signUpLogic() {
        val fullName = binding.fullName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.phone.text.toString()
        val password = binding.confirmPassword.text.toString()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            requireView().showToastShort("Please Enter valid email")
            return
        }
        if (fullName.isEmpty()) {
            requireView().showToastShort("Please Enter your name")
            return
        }
        if (phone.isEmpty()) {
            requireView().showToastShort("Please Enter your phone number")
            return
        }
        if (password.isEmpty() || password.length < 8) {
            requireView().showToastShort("Please Enter valid password")
            return
        }

        val signUpUser = SignUpUser(fullName, email, phone, password)
        viewModel.signUp(signUpUser)

        viewModel.signUpState.observe(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is NetWorkResponseState.Loading -> {
                    binding.progress.visible()
                    binding.signUp.isEnabled = false
                }

                is NetWorkResponseState.Success -> {
                    binding.progress.gone()
                    binding.signUp.isEnabled = true
                    findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToHomeFragment())
//                    requireView().showToastShort("${R.string.welcome} ${signUpState.result.userName}")
                    val editor = sharedPref.edit()
                    editor.putString(Constants.SHARED_PREF_USERNAME_KEY, fullName)
                    editor.putString(Constants.SHARED_PREF_USERPASSWORD, password)
                    editor.putString(Constants.SHARED_PREF_USERPHONE, phone)
                    editor.putString(Constants.SHARED_PREF_USEREMAIL, email)

//                    editor.putString(Constants.SHARED_PREF_USERID_KEY, signUpState.result.id)
                    editor.apply()
                }

                is NetWorkResponseState.Error -> {
                    binding.progress.gone()
                    binding.signUp.isEnabled = true
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


}