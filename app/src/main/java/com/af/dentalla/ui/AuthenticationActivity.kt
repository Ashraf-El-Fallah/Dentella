package com.af.dentalla.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.af.dentalla.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

private lateinit var binding: ActivityAuthenticationBinding

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        binding.root
        setContentView(binding.root)
    }
}