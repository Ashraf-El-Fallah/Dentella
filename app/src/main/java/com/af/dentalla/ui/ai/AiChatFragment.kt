package com.af.dentalla.ui.ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
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
        setOnClicks()
    }

    private fun backToSplashAi() {
        binding.backToSplash.root.setOnClickListener {
            findNavController().navigate(AiChatFragmentDirections.actionAiChatFragmentToFirstSplashAiFragment())
        }
    }

    private fun sendToWebsiteModel() {
        val url = getString(R.string.ai_website_model_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setOnClicks() {
        val onClickListener = View.OnClickListener {
            sendToWebsiteModel()
        }

        binding.apply {
            sendBtn.setOnClickListener(onClickListener)
            aiSearch.setOnClickListener(onClickListener)
        }
    }

}