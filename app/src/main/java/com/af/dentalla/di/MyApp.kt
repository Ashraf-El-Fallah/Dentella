package com.af.dentalla.di

import android.app.Application
import com.af.dentalla.utils.SpecialtyUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SpecialtyUtil.initialize(this)
    }
}