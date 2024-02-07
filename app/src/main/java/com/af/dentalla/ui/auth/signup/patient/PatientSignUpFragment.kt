package com.af.dentalla.ui.auth.signup.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentDoctorSignUpBinding
import com.af.dentalla.databinding.FragmentPatientSignUpBinding
import com.af.dentalla.ui.auth.signup.doctor.DoctorSignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientSignUpFragment : Fragment() {

    private val viewModel: PatientSignUpViewModel by viewModels()
    private lateinit var binding: FragmentPatientSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPatientSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}