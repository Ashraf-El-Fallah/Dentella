package com.af.dentalla.ui

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
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
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
//        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeNavController()

        lifecycleScope.launch {
            AccountManager.accountType = dataStorePreferencesService.getAccountType()
            setupUI()
        }

    }

    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView, View.TRANSLATION_Y, 0f, -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 1000L

                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        } else {
            setTheme(R.style.Theme_Dentalla)
        }
    }

    private fun setupUI() {
        initBottomNavigation()
        navigateToHomeFragment()
        showDialogDependingOnUserType()
        addPostObserver()
    }


    override fun onBackPressed() {
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

    private fun initializeNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun navigateToHomeFragment() {
        val startDestination =
            if (AccountManager.accountType == AccountManager.AccountType.DOCTOR) {
                R.id.doctorHomeFragment
            } else {
                R.id.patientHomeFragment
            }

        navController.navigate(startDestination)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.doctorHomeFragment || destination.id == R.id.patientHomeFragment ||
                destination.id == R.id.firstSplashAiFragment || destination.id == R.id.settingFragment || destination.id == R.id.articlesFragment
            ) {
                binding.bottomNavigationView.visible()
                binding.centerButton.visible()
            } else {
                binding.bottomNavigationView.gone()
                binding.centerButton.gone()
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
                    Toast.makeText(this, R.string.send_post_successfully, Toast.LENGTH_SHORT)
                        .show()
                }

                is ScreenState.Error -> {
                    binding.progress.root.gone()
                    if (addPostState.statusCode == 401) {
                        Toast.makeText(
                            this,
                            R.string.want_to_login_again,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            R.string.server_error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
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
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}