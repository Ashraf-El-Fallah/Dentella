package com.af.dentalla.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.af.dentalla.R
import com.af.dentalla.databinding.ActivityHomeBinding
import com.af.dentalla.ui.doctor.DoctorHomeFragment
import com.af.dentalla.ui.patient.homeScreen.PatientHomeFragment
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
    }

    private fun navigateToHomeFragment() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (accountType == AccountManager.AccountType.DOCTOR) {
            val doctorFragment = DoctorHomeFragment()
            transaction.replace(R.id.nav_host_home_fragment, doctorFragment)
//            transaction.commit()
        } else if (accountType == AccountManager.AccountType.PATIENT) {
            val patientFragment = PatientHomeFragment()
            transaction.replace(R.id.nav_host_home_fragment, patientFragment)
//            transaction.commit()
        }
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}