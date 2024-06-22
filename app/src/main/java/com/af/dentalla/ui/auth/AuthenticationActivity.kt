package com.af.dentalla.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.af.dentalla.databinding.ActivityAuthenticationBinding
import com.af.dentalla.ui.BaseActivity
import com.af.dentalla.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {
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
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}