package com.af.dentalla.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.af.dentalla.R
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.databinding.ActivityAuthenticationBinding
import com.af.dentalla.ui.HomeActivity
import com.af.dentalla.ui.auth.login.LoginFragment
import com.af.dentalla.ui.selectUserType.SelectUserTypeScreenFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStorePreferencesService: DataStorePreferencesService
    private lateinit var binding: ActivityAuthenticationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            val token = dataStorePreferencesService.getToken()
            if (token.isNullOrEmpty()) {
                // No token found, user needs to log in
                supportFragmentManager.commit {
                    replace(R.id.nav_host_auth_fragment, SelectUserTypeScreenFragment())
                }
            } else {
                // Token found, navigate to HomeActivity
                startActivity(Intent(this@AuthenticationActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}