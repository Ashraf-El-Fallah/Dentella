package com.af.dentalla.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSettingBinding
import com.af.dentalla.ui.HomeActivity
import com.af.dentalla.ui.auth.AuthenticationActivity
import com.af.dentalla.ui.setting.changeLanguage.ChangeLanguageBottomSheetFragment
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
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
                    binding.progress.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    val intent =
                        Intent(
                            this@SettingFragment.requireContext(),
                            AuthenticationActivity::class.java
                        )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                is ScreenState.Error -> {
                    binding.progress.progress.gone()
                }
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage(R.string.want_to_logout)
            .setCancelable(false)
            .setPositiveButton(R.string.logout) { dialog, id ->
                settingViewModel.logout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        // Customize the buttons
        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        // Ensure buttons text maintain the correct capitalization
        positiveButton?.let {
            it.setText(R.string.logout)
            it.isAllCaps = false
            it.setTextColor(ContextCompat.getColor(requireContext(), R.color.shade_of_red))
        }

        negativeButton?.let {
            it.setText(R.string.cancel)
            it.isAllCaps = false
            it.setTextColor(ContextCompat.getColor(requireContext(), R.color.cyan_blue))
        }
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