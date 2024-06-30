package com.af.dentalla.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentLoginBinding
import com.af.dentalla.ui.HomeActivity
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastShort
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
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
                viewModel.loginUserLogic(
                    accountType.toString(),
                    userName,
                    null,
                    password
                )
            } else if (accountType == AccountManager.AccountType.DOCTOR) {
                val email = binding.editTextEmail.text.toString()
                viewModel.loginUserLogic(
                    accountType.toString(),
                    null,
                    email,
                    password
                )
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
                    val intent =
                        Intent(this@LoginFragment.requireContext(), HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                is ScreenState.Error -> {
                    binding.progressBar.progress.gone()
                    binding.buttonSignIn.isEnabled = true
                    val errorMessage = loginState.errorMessageCode?.let { getString(it) }
                        ?: getString(R.string.network_error)
                    context?.showToastShort(errorMessage)
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
