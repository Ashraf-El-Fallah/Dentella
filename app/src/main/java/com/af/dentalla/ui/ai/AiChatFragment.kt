package com.af.dentalla.ui.ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.databinding.FragmentAiChatBinding
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
        sendUserToAiModel()
    }

    private fun backToSplashAi() {
        binding.backToSplash.setOnClickListener {
           findNavController().navigate(AiChatFragmentDirections.actionAiChatFragmentToFirstSplashAiFragment())
        }
    }

    private fun sendUserToAiModel() {
        binding.sendBtn.setOnClickListener {
            val url = "https://huggingface.co/spaces/OmarEllethy/DENTELLA"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

}