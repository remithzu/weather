package com.rmtz.weather.repository

import com.rmtz.weather.networks.RetrofitInstance

class WeatherRepository {
    suspend fun getCurrentWeather(location: String) = RetrofitInstance.getInstance().getCurrentWeather(location)

    suspend fun getGeoLocation(location: String) = RetrofitInstance.getInstance().getGeoLocation(location)
}