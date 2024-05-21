package com.af.dentalla.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSettingBinding
import com.af.dentalla.ui.auth.AuthenticationActivity
import com.af.dentalla.ui.setting.changeLanguage.ChangeLanguageBottomSheetFragment
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment : Fragment() {
    // TODO: remove unused dataStorePreferencesService
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
                            requireContext(),
                            AuthenticationActivity::class.java
                        )
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
            .setPositiveButton(R.string.yes) { dialog, id ->
                settingViewModel.logout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, id ->
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