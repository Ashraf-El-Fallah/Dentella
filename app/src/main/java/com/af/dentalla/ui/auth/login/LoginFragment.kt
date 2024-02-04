package com.af.dentalla.ui.auth.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.User
import com.af.dentalla.databinding.FragmentLoginAccountBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginAccountBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoginButton()
    }

    private fun setUpLoginButton() {
        binding.signIn.setOnClickListener { loginLogic() }
    }

    private fun loginLogic() {
        val userName = binding.etUserName.text.toString()
        val password = binding.etPassword.text.toString()

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                context,
                getString(R.string.please_add_user_name_or_password),
                Toast.LENGTH_SHORT
            )
            return
        }


        val user = User(userName, password)
        viewModel.login(user)

        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                is NetWorkResponseState.Loading -> {
                    binding.progressBar.isVisible
                    binding.signIn.isEnabled = false
                }

                is NetWorkResponseState.Success -> {
                    binding.progressBar.isGone
                    binding.signIn.isEnabled = true
                    findNavController().navigate(LoginFragmentDirections.actionLoginAccountFragmentToHomeFragment5())
                    Toast.makeText(
                        context,
                        "${R.string.welcome} ${loginState.result.userName}",
                        Toast.LENGTH_SHORT
                    )
                    val editor = sharedPref.edit()
                    editor.putString("username", userName)
                    editor.putString("password", password)
                    editor.apply()
                }

                is NetWorkResponseState.Error -> {
                    binding.progressBar.isGone
                    binding.signIn.isEnabled = true
                    Toast.makeText(context, R.string.check_username_pass, Toast.LENGTH_SHORT)
                }
            }
        }
    }

//    private fun setOnClicks() {
//        val navigateToCreateAccount =
//            LoginFragmentDirections.actionLoginAccountFragmentToCreateAccountFragment()
//        binding.signUp.setOnClickListener {
//            view?.findNavController()
//                ?.navigate(navigateToCreateAccount)
//        }
//
//        val navigateToResetPassword =
//            LoginFragmentDirections.actionLoginAccountFragmentToForgetPasswordFragment()
//        binding.forgetPassword.setOnClickListener {
//            view?.findNavController()
//                ?.navigate(navigateToResetPassword)
//        }
//
//        val navigateToHomeScreen =
//            LoginFragmentDirections.actionLoginAccountFragmentToHomeFragment5()
//        binding.signIn.setOnClickListener {
//
//        }
//
//    }


}