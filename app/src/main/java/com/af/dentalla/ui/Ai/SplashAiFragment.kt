package com.af.dentalla.ui.Ai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentSplashAiBinding
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.safeNavigate
import com.af.dentalla.utilities.visible


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
    }


    private fun hideFirstScreen() {
        binding.imgViewAi.gone()
        binding.textViewAiIntro.gone()
    }

    private fun showSecondScreen() {
        binding.imgViewAi2.visible()
        binding.textViewAiIntro2.visible()
    }

}