package com.af.dentalla.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentConfirmationCodeBinding
import com.af.dentalla.databinding.FragmentCreateAccountBinding

class ConfirmationCodeFragment : Fragment() {
    private var _binding: FragmentConfirmationCodeBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentConfirmationCodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}