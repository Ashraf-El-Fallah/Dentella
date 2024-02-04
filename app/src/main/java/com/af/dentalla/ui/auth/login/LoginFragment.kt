package com.af.dentalla.ui.auth.login

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.common.Constants.SHARED_PREF_USERID_KEY
import com.af.dentalla.common.Constants.SHARED_PREF_USERNAME_KEY
import com.af.dentalla.common.Constants.SHARED_PREF_USERPASSWORD
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.dto.LoginUser
import com.af.dentalla.databinding.FragmentLoginAccountBinding
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible

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
            requireView().showToastShort(getString(R.string.please_add_user_name_or_password))
            return
        }


        val loginUser = LoginUser(userName, password)
        viewModel.login(loginUser)

        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                is NetWorkResponseState.Loading -> {
                    binding.progressBar.visible()
                    binding.signIn.isEnabled = false
                }

                is NetWorkResponseState.Success -> {
                    binding.progressBar.gone()
                    binding.signIn.isEnabled = true
                    findNavController().navigate(LoginFragmentDirections.actionLoginAccountFragmentToHomeFragment5())
                    requireView().showToastShort("${R.string.welcome} ${loginState.result.userName}")
                    val editor = sharedPref.edit()
                    editor.putString(SHARED_PREF_USERNAME_KEY, userName)
                    editor.putString(SHARED_PREF_USERPASSWORD, password)
                    editor.putString(SHARED_PREF_USERID_KEY, loginState.result.id)
                    editor.apply()
                }

                is NetWorkResponseState.Error -> {
                    binding.progressBar.gone()
                    binding.signIn.isEnabled = true
                    requireView().showToastShort(R.string.check_username_pass.toString())
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