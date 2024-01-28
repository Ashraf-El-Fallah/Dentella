package com.af.dentalla.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.af.dentalla.databinding.FragmentConfirmationCodeBinding

class ConfirmationCodeFragment : Fragment() {
    private var _binding: FragmentConfirmationCodeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }


    private fun setOnClicks() {
        val action =
            ConfirmationCodeFragmentDirections.actionConfirmationCodeFragmentToChangePasswordFragment()
        binding.Continue.setOnClickListener {
            view?.findNavController()
                ?.navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}