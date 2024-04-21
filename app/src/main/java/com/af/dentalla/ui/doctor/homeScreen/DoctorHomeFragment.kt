package com.af.dentalla.ui.doctor.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.R
import com.af.dentalla.databinding.FragmentDoctorHomeBinding
import com.af.dentalla.databinding.FragmentPatientHomeBinding
import com.af.dentalla.databinding.ProgressBarBinding
import com.af.dentalla.utilities.ScreenState
import com.af.dentalla.utilities.gone
import com.af.dentalla.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorHomeFragment : Fragment() {
    private lateinit var binding: FragmentDoctorHomeBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    private val doctorHomeViewModel: DoctorHomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorHomeBinding.inflate(inflater, container, false)
        progressBarBinding = ProgressBarBinding.bind(binding.progressBar.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postObserver()
    }

    private fun postObserver() {
        doctorHomeViewModel.allPosts.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> progressBarBinding.progress.visible()
                is ScreenState.Success -> {
                    progressBarBinding.progress.gone()
                    binding.recyclerViewPosts.apply {
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        adapter = PostsAdapter().apply {
                            submitList(it.uiData)
                        }
                    }
                }
                is ScreenState.Error -> progressBarBinding.progress.gone()
            }
        }
    }


}