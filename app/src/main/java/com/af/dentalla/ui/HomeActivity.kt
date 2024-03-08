package com.af.dentalla.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.ActivityHomeBinding
import com.af.dentalla.ui.patient.homeScreen.AddPostDialog
import com.af.dentalla.utilities.AccountManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
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
        if (accountType == AccountManager.AccountType.PATIENT) {
            binding.centerButton.setOnClickListener {
                AddPostDialog(this).show()
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