package com.af.dentalla.ui.fragments.doctorauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.af.dentalla.databinding.FragmentLoginAccountBinding

class LoginAccountFragment : Fragment() {
    private var _binding: FragmentLoginAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        val navigateToCreateAccount =
            LoginAccountFragmentDirections.actionLoginAccountFragmentToCreateAccountFragment()
        binding.signUp.setOnClickListener {
            view?.findNavController()
                ?.navigate(navigateToCreateAccount)
        }

        val navigateToResetPassword =
            LoginAccountFragmentDirections.actionLoginAccountFragmentToForgetPasswordFragment()
        binding.forgetPassword.setOnClickListener {
            view?.findNavController()
                ?.navigate(navigateToResetPassword)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}