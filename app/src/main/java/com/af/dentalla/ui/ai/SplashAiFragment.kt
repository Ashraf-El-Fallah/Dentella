package com.af.dentalla.ui.ai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentSplashAiBinding
import com.af.dentalla.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint


class SplashAiFragment : Fragment() {
    private lateinit var binding: FragmentSplashAiBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashAiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        skipToAiScreen()
    }

    private fun skipToAiScreen() {
        binding.arrowToSkip.setOnClickListener {
            findNavController().safeNavigate(SplashAiFragmentDirections.actionFirstSplashAiFragmentToAiChatFragment())
        }
        binding.skip.setOnClickListener {
            findNavController().safeNavigate(SplashAiFragmentDirections.actionFirstSplashAiFragmentToAiChatFragment())
        }
    }

}