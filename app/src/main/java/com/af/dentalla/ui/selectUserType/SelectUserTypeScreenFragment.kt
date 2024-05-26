package com.af.dentalla.ui.selectUserType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSelectUserTypeScreenBinding
import com.af.dentalla.utils.AccountManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SelectUserTypeScreenFragment : Fragment() {
    private lateinit var binding: FragmentSelectUserTypeScreenBinding

    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
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
            updateAccountType(AccountManager.AccountType.DOCTOR)
        }
        binding.buttonPatient.setOnClickListener {
            updateAccountType(AccountManager.AccountType.PATIENT)
        }

    }

    private fun updateAccountType(accountType: AccountManager.AccountType) {
        AccountManager.accountType = accountType
        lifecycleScope.launch {
            dataStorePreferencesService.saveAccountType(accountType)
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(SelectUserTypeScreenFragmentDirections.actionSplashScreenToLoginOrSignupFragment())
    }


}