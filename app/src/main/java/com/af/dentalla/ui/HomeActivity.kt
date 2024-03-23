package com.af.dentalla.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.dentalla.R
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.databinding.ActivityHomeBinding
import com.af.dentalla.ui.articles.AddArticleDialog
import com.af.dentalla.ui.articles.AddArticleDialogListener
import com.af.dentalla.ui.patient.homeScreen.AddPostDialog
import com.af.dentalla.ui.patient.homeScreen.AddPostDialogListener
import com.af.dentalla.ui.patient.homeScreen.PatientHomeViewModel
import com.af.dentalla.utilities.AccountManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val patientHomeViewModel: PatientHomeViewModel by viewModels()
    private val accountType = AccountManager.accountType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        navigateToHomeFragment()
        showDialogDependingOnUserType()
    }

    private fun showDialogDependingOnUserType() {
        binding.centerButton.setOnClickListener {
            if (accountType == AccountManager.AccountType.PATIENT) {
                AddPostDialog(this,
                    object : AddPostDialogListener {
                        override fun onPostAdded(post: Post) {
                            patientHomeViewModel.addPots(post)
                        }
                    }).show()
            } else {
                findNavController(R.id.nav_host_home_fragment).navigate(R.id.addCardFragment)
            }
        }
    }

    private fun navigateToHomeFragment() {
        val startDestination = if (accountType == AccountManager.AccountType.DOCTOR) {
            R.id.doctorHomeFragment
        } else {
            R.id.patientHomeFragment
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(startDestination)
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}