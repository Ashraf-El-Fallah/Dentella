package com.af.dentalla.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSettingBinding
import com.af.dentalla.ui.auth.AuthenticationActivity
import com.af.dentalla.ui.setting.changeLanguage.ChangeLanguageBottomSheetFragment
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
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
        lifecycleScope.launch {
            AccountManager.accountType = dataStorePreferencesService.getAccountType()
            if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
                binding.textViewDelete.text = getString(R.string.delete_cards)
            }
        }
        goToEditProfile()
        showBottomSheet()
        showLogoutDialog()
        logoutObserver()
        showDeleteInfoDialog()
        deleteInfoObserver()
    }

    private fun showLogoutDialog() {
        binding.cardViewLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showDeleteInfoDialog() {
        binding.cardViewDeleteInfo.setOnClickListener {
            showDeleteDialog()
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
                    if (state.message == "HTTP 401 Unauthorized") {
                        Toast.makeText(
                            requireContext(),
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val errorMessage = state.message ?: getString(R.string.network_error)
                        Toast.makeText(
                            requireContext(),
                            errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

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


    private fun showDeleteDialog() {
        var message = getString(R.string.want_to_delete_Posts)
        if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
            message = getString(R.string.want_to_delete_cards)
        }
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.delete) { dialog, id ->
                settingViewModel.deleteUserInfo()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.dismiss()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        positiveButton?.let {
            it.setText(R.string.delete)
            it.isAllCaps = false
            it.setTextColor(ContextCompat.getColor(requireContext(), R.color.shade_of_red))
        }

        negativeButton?.let {
            it.setText(R.string.cancel)
            it.isAllCaps = false
            it.setTextColor(ContextCompat.getColor(requireContext(), R.color.cyan_blue))
        }
    }

    private fun deleteInfoObserver() {
        settingViewModel.deleteInfoState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    binding.progress.progress.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.progress.gone()
                    Toast.makeText(
                        requireContext(),
                        R.string.delete_info_successfully,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ScreenState.Error -> {
                    binding.progress.progress.gone()
                    when (state.message) {
                        "HTTP 401 Unauthorized" -> {
                            Toast.makeText(
                                requireContext(),
                                R.string.want_to_login_again,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        "HTTP error 404" -> {
                            Toast.makeText(
                                requireContext(),
                                R.string.cannot_delete_info,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                requireContext(),
                                R.string.network_error,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
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