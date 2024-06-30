package com.af.dentalla.ui.doctor.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.af.dentalla.databinding.FragmentDoctorHomeBinding
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.safeNavigate
import com.af.dentalla.utils.showToastLong
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorHomeFragment : Fragment() {
    private lateinit var binding: FragmentDoctorHomeBinding
    private val doctorHomeViewModel: DoctorHomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postObserver()
        navigateToSearchFragment()
    }

    private fun navigateToSearchScreen() {
        findNavController().safeNavigate(DoctorHomeFragmentDirections.actionDoctorHomeFragmentToSearchFragment())
    }

    private fun navigateToSearchFragment() {
        binding.topScreen.editTextSearchHome.setOnClickListener {
            navigateToSearchScreen()
        }
    }


    private fun postObserver() {
        doctorHomeViewModel.allPosts.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> binding.progressBar.progress.visible()
                is ScreenState.Success -> {
                    binding.progressBar.progress.gone()
                    binding.recyclerViewPosts.apply {
                        layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        adapter = PostsAdapter().apply {
                            submitList(it.uiData)
                        }
                    }
                }

                is ScreenState.Error -> {
                    binding.progressBar.progress.gone()
                    context?.showToastLong(it.message.toString())
                }
            }
        }
    }


}