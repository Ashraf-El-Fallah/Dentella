package com.af.dentalla.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentChangeLanguageBottomSheetBinding
import com.af.dentalla.databinding.FragmentSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangeLanguageBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChangeLanguageBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeLanguageBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}