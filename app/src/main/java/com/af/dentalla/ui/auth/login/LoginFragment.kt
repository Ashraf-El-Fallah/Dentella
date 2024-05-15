package com.af.dentalla.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.remote.requests.LoginDoctor
import com.af.dentalla.data.remote.requests.LoginPatient
import com.af.dentalla.databinding.FragmentLoginBinding
import com.af.dentalla.ui.HomeActivity
import com.af.dentalla.ui.base.BaseFragment
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val accountType = AccountManager.accountType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginObserver()
        selectTheUserType()
        passUserDataToViewModel()


        binding.textViewSignUp.setOnClickListener {
            findNavController().safeNavigate(
                LoginFragmentDirections.actionLoginAccountFragmentToSignUpFragment()
            )
        }
    }

    private fun passUserDataToViewModel() {
        binding.buttonSignIn.setOnClickListener {
            val password = binding.editTextPasswordLogin.text.toString()
            if (accountType == AccountManager.AccountType.PATIENT) {
                val userName = binding.editTextUserName.text.toString()
                if (isUserNameValid(userName) && isPasswordValid(password)) {
                    val loginPatient = LoginPatient(
                        userName = userName, passWord = password
                    )
                    viewModel.loginUserLogic(loginPatient)
                }
            } else if (accountType == AccountManager.AccountType.DOCTOR) {
                val email = binding.editTextEmail.text.toString()
                if (isEmailValid(email) && isPasswordValid(password)) {
                    val loginDoctor = LoginDoctor(email = email, passWord = password)
                    viewModel.loginUserLogic(loginDoctor)
                }
            }
        }
    }

    private fun loginObserver() {
        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                is ScreenState.Loading -> {
                    binding.progressBar.progress.visible()
                    binding.buttonSignIn.isEnabled = false
                }

                is ScreenState.Success -> {
                    binding.progressBar.progress.gone()
                    binding.buttonSignIn.isEnabled = true
//                    findNavController().safeNavigate(LoginFragmentDirections.actionLoginAccountFragmentToHomeFragment5())
//                    (activity as AuthenticationActivity).navigateToHomeActivity()
                    val intent =
                        Intent(this@LoginFragment.requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                }

                is ScreenState.Error -> {
                    binding.progressBar.progress.gone()
                    binding.buttonSignIn.isEnabled = true
//                    requireView().showToastShort("Incorrect Personal information")
                }
            }
        }
    }

    private fun selectTheUserType() {
        if (accountType == AccountManager.AccountType.PATIENT) {
            binding.textInputUserName.visible()
            binding.textInputEmail.gone()
        } else if (accountType == AccountManager.AccountType.DOCTOR) {
            binding.textInputUserName.gone()
            binding.textInputEmail.visible()
        }
    }
}


//    private fun loginLogic() {
//        val userName = binding.etUserName.text.toString()
//        val password = binding.etPassword.text.toString()
//
//        if (userName.isEmpty() || password.isEmpty()) {
//            requireView().showToastShort(getString(R.string.please_add_user_name_or_password))
//            return
//        }
//
//
//        val loginUser = LoginUser(userName, password)
//        viewModel.login(loginUser)
//
//        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
//            when (loginState) {
//                is NetWorkResponseState.Loading -> {
//                    binding.progressBar.visible()
//                    binding.signIn.isEnabled = false
//                }
//
//                is NetWorkResponseState.Success -> {
//                    binding.progressBar.gone()
//                    binding.signIn.isEnabled = true
//                    findNavController().navigate(LoginFragmentDirections.actionLoginAccountFragmentToHomeFragment5())
//                    requireView().showToastShort("${R.string.welcome} ${loginState.result.userName}")
////                    val editor = sharedPref.edit()
////                    editor.putString(SHARED_PREF_USERNAME_KEY, userName)
////                    editor.putString(SHARED_PREF_USERPASSWORD, password)
////                    editor.putString(SHARED_PREF_USERID_KEY, loginState.result.id)
////                    editor.apply()
//                }
//
//                is NetWorkResponseState.Error -> {
//                    binding.progressBar.gone()
//                    binding.signIn.isEnabled = true
//                    requireView().showToastShort(R.string.check_username_pass.toString())
//                }
//            }
//        }
//    }

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
