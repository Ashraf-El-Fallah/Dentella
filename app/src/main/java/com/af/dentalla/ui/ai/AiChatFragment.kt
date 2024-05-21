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
        //TODO: Do it for all project
        // if you clicked on backToSplash multiple time it will navigate many time
        // to prevent such an error try to add a delay
        // see: https://stackoverflow.com/a/56462539/11260654
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