package com.af.dentalla.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSettingBinding
import com.af.dentalla.ui.HomeActivity
import com.af.dentalla.ui.auth.AuthenticationActivity
import com.af.dentalla.ui.setting.updateProfile.updatePassword.UpdatePasswordViewModel
import com.af.dentalla.utils.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment : Fragment() {
    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToEditProfile()
        showBottomSheet()
        showLogoutDialog()
        logoutObserver()

    }

    private fun showLogoutDialog() {
        binding.cardViewLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun logoutObserver() {
        settingViewModel.logoutState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    // Show loading indicator
                }

                is ScreenState.Success -> {
//                    val intent =
//                        Intent(this@SettingFragment.requireContext(), AuthenticationActivity::class.java)
//                    startActivity(intent)
                    lifecycleScope.launch {
                        dataStorePreferencesService.saveToken(null)
                    }
                    navigateToAuthenticationActivity()
                }

                is ScreenState.Error -> {
                    // Handle logout error
                }
            }
        }
    }

    private fun navigateToAuthenticationActivity() {
        val intent = Intent(requireContext(), AuthenticationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }


    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Are you sure you want to logout?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Logout and clear token
                settingViewModel.logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun goToEditProfile() {
        binding.cardViewEditProfile.setOnClickListener {
            val action =
                SettingFragmentDirections.actionPatientProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun showBottomSheet() {
        binding.cardViewChangeLanguage.setOnClickListener {
            val bottomSheetFragment = ChangeLanguageBottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

}