package com.af.dentalla.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentLoginOrSignupBinding

class LoginOrSignupFragment : Fragment() {
    private lateinit var binding: FragmentLoginOrSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginOrSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(LoginOrSignupFragmentDirections.actionLoginOrSignupFragmentToSignUpFragment())
        }
        binding.buttonSignIn.setOnClickListener {
            findNavController().navigate(LoginOrSignupFragmentDirections.actionLoginOrSignupFragmentToLoginAccountFragment())
        }
    }
}