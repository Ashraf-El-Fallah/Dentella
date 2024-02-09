package com.af.dentalla.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentSplashScreenBinding
import com.af.dentalla.utilities.AccountManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.buttonDoctor.setOnClickListener {
            AccountManager.accountType = AccountManager.AccountType.DOCTOR
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenToLoginAccountFragment())
        }
        binding.buttonPatient.setOnClickListener {
            AccountManager.accountType = AccountManager.AccountType.PATIENT
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenToLoginAccountFragment())
        }

    }

}