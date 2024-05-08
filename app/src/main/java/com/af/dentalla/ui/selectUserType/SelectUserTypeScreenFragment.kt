package com.af.dentalla.ui.selectUserType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentSelectUserTypeScreenBinding
import com.af.dentalla.utils.AccountManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectUserTypeScreenFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserTypeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectUserTypeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        binding.buttonDoctor.setOnClickListener {
            AccountManager.accountType = AccountManager.AccountType.DOCTOR
            findNavController().navigate(SelectUserTypeScreenFragmentDirections.actionSplashScreenToLoginAccountFragment())
        }
        binding.buttonPatient.setOnClickListener {
            AccountManager.accountType = AccountManager.AccountType.PATIENT
            findNavController().navigate(SelectUserTypeScreenFragmentDirections.actionSplashScreenToLoginAccountFragment())
        }

    }

}