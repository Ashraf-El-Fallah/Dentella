package com.af.dentalla.ui.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.af.dentalla.R
import com.af.dentalla.databinding.ActivityAuthenticationBinding
import com.af.dentalla.ui.BaseActivity
import com.af.dentalla.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthenticationActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private val authViewModel: AuthenticationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
//        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isUserLoginObserver()
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