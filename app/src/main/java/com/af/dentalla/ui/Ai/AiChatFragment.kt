package com.af.dentalla.ui.Ai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentAiChatBinding
import com.af.dentalla.databinding.FragmentSplashAiBinding
import com.af.dentalla.utilities.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AiChatFragment : Fragment() {
    private lateinit var binding: FragmentAiChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToSplashAi()
    }

    private fun backToSplashAi() {
        binding.backToSplash.setOnClickListener {
            findNavController().safeNavigate(AiChatFragmentDirections.actionAiChatFragmentToFirstSplashAiFragment())
        }
    }

}