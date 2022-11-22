package com.rmtz.weather.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rmtz.weather.ui.weather.WeatherViewModel
import com.rmtz.weather.repository.WeatherRepository
import com.rmtz.weather.ui.location.LocationViewModel

class ViewModelProviderFactory(
    val application: Application,
    val repository: WeatherRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(application, this.repository) as T
        }
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(application, this.repository) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}