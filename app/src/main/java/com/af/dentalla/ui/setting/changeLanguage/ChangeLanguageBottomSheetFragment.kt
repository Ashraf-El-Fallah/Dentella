package com.af.dentalla.ui.setting.changeLanguage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.FragmentChangeLanguageBottomSheetBinding
import com.af.dentalla.utils.LocaleUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChangeLanguageBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChangeLanguageBottomSheetBinding

    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeLanguageBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChangeLanguageArabic.setOnClickListener {
            changeLanguage("ar")
        }
        binding.buttonChangeLanguageEnglish.setOnClickListener {
            changeLanguage("en")
        }
    }

    private fun changeLanguage(language: String) {
        lifecycleScope.launch {
            dataStorePreferencesService.saveLanguage(language = language)
            LocaleUtils.setLocale(requireContext(), language)
            restartApp()
        }
    }

    private fun restartApp() {
        val intent = requireActivity().baseContext.packageManager
            .getLaunchIntentForPackage(requireActivity().baseContext.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().startActivity(intent)
        requireActivity().finish()

    }
}