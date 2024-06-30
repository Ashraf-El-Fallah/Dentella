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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.data.local.datastore.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentSettingBinding
import com.af.dentalla.ui.auth.AuthenticationActivity
import com.af.dentalla.ui.setting.changeLanguage.ChangeLanguageBottomSheetFragment
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastLong
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
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.cardViewLogout.setOnClickListener {
            showConfirmationDialog(
                R.string.want_to_logout,
                R.string.logout,
                R.string.cancel,
                { settingViewModel.logout() },
                R.color.shade_of_red,
                R.color.cyan_blue
            )
        }

        binding.cardViewDeleteInfo.setOnClickListener {
            showConfirmationDialog(
                if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) R.string.want_to_delete_cards else R.string.want_to_delete_Posts,
                R.string.delete,
                R.string.cancel,
                { settingViewModel.deleteUserInfo() },
                R.color.shade_of_red,
                R.color.cyan_blue
            )
        }

        binding.cardViewEditProfile.setOnClickListener {
            val action =
                SettingFragmentDirections.actionPatientProfileFragmentToEditProfileFragment()
            findNavController().safeNavigate(action)
        }

        binding.cardSavedArticles.setOnClickListener {
            val action =
                SettingFragmentDirections.actionSettingFragmentToSavedArticlesFragment()
            findNavController().safeNavigate(action)
        }


        binding.cardViewChangeLanguage.setOnClickListener {
            val bottomSheetFragment = ChangeLanguageBottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun setupObservers() {
        settingViewModel.logoutState.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { state ->
                handleScreenState(
                    state = state,
                    notFoundMessage = R.string.server_error
                ) {
                    val intent = Intent(
                        this@SettingFragment.requireContext(),
                        AuthenticationActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }

        settingViewModel.deleteInfoState.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { state ->
                handleScreenState(
                    state = state,
                    notFoundMessage = R.string.cannot_delete_info,
                    successfulMessage = R.string.delete_info_successfully
                )
            }
        }
    }

    private fun showConfirmationDialog(
        message: Int,
        positiveText: Int,
        negativeText: Int,
        positiveAction: () -> Unit,
        positiveColor: Int,
        negativeColor: Int
    ) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positiveText) { dialog, _ ->
                positiveAction()
                dialog.dismiss()
            }
            .setNegativeButton(negativeText) { dialog, _ -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
            setText(positiveText)
            isAllCaps = false
            setTextColor(ContextCompat.getColor(requireContext(), positiveColor))
        }
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.apply {
            setText(negativeText)
            isAllCaps = false
            setTextColor(ContextCompat.getColor(requireContext(), negativeColor))
        }
    }

    private fun handleScreenState(
        state: ScreenState<*>,
        successfulMessage: Int? = null,
        notFoundMessage: Int?,
        successAction: (() -> Unit)? = null
    ) {
        when (state) {
            is ScreenState.Loading -> binding.progress.progress.visible()
            is ScreenState.Success -> {
                binding.progress.progress.gone()
                successAction?.invoke()
                successfulMessage?.let {
                    context?.showToastLong(getString(it))
                }
            }

            is ScreenState.Error -> {
                binding.progress.progress.gone()
                val errorMessageRes = when (state.statusCode) {
                    401 -> R.string.want_to_login_again
                    404 -> notFoundMessage
                    else -> R.string.server_error
                }
                errorMessageRes?.let {
                    context?.showToastLong(getString(it))
                }
            }
        }
    }
}