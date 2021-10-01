package com.agaperra.weatherforecast

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ForecastApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}