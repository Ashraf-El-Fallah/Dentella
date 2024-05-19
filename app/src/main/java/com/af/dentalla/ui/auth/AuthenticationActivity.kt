package com.af.dentalla.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.ActivityAuthenticationBinding
import com.af.dentalla.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    private lateinit var binding: ActivityAuthenticationBinding
    private val authViewModel: AuthenticationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isUserLoginObserver()
    }

    private fun isUserLoginObserver() {
        authViewModel.isUserLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}