package com.rmtz.weather.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class WeatherApplication: MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}