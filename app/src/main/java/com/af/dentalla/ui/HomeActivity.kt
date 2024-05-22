package com.af.dentalla.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.dentalla.R
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.databinding.ActivityHomeBinding
import com.af.dentalla.ui.patient.homeScreen.AddPostDialog
import com.af.dentalla.ui.patient.homeScreen.AddPostDialogListener
import com.af.dentalla.ui.patient.homeScreen.PatientHomeViewModel
import com.af.dentalla.utils.AccountManager
import com.af.dentalla.utils.ScreenState
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val patientHomeViewModel: PatientHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            AccountManager.accountType = dataStorePreferencesService.getAccountType()
            setupUI()
        }

    }

    private fun setupUI() {
        initBottomNavigation()
        navigateToHomeFragment()
        showDialogDependingOnUserType()
        addPostObserver()
    }


    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (navController.currentDestination?.id == R.id.doctorHomeFragment ||
            navController.currentDestination?.id == R.id.patientHomeFragment ||
            navController.currentDestination?.id == R.id.articlesFragment ||
            navController.currentDestination?.id == R.id.settingFragment ||
            navController.currentDestination?.id == R.id.firstSplashAiFragment
        ) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun navigateToHomeFragment() {
        val startDestination =
            if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
                R.id.doctorHomeFragment
            } else {
                R.id.patientHomeFragment
            }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(startDestination)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addCardFragment || destination.id == R.id.editProfileFragment || destination.id == R.id.aiChatFragment) {
                binding.bottomNavigationView.gone()
                binding.centerButton.gone()
            } else {
                binding.bottomNavigationView.visible()
                binding.centerButton.visible()
            }
        }
    }


    private fun addPostObserver() {
        patientHomeViewModel.addPostState.observe(this) { addPostState ->
            when (addPostState) {
                is ScreenState.Loading -> {
                    binding.progress.root.visible()
                }

                is ScreenState.Success -> {
                    binding.progress.root.gone()
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                }
            }
        }
    }

    private fun showDialogDependingOnUserType() {
        binding.centerButton.setOnClickListener {
            if (AccountManager.accountType == AccountManager.AccountType.PATIENT) {
                AddPostDialog(this,
                    object : AddPostDialogListener {
                        override fun onPostAdded(post: Post) {
                            patientHomeViewModel.addPost(post)
                        }
                    }).show()
            } else {
                findNavController(R.id.nav_host_home_fragment).navigate(R.id.addCardFragment)
            }
        }
    }


    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}