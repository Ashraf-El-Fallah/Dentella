package com.af.dentalla.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.af.dentalla.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClicks()
    }

    private fun setOnClicks() {
        val action = SplashScreenFragmentDirections.actionSplashScreenToLoginScreen()
        binding.doctor.setOnClickListener {
            view?.findNavController()
                ?.navigate(action)
        }

        binding.patient.setOnClickListener {
            view?.findNavController()
                ?.navigate(action)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}