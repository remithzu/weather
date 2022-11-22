package com.rmtz.weather.networks

import com.rmtz.weather.commons.Const
import com.rmtz.weather.models.CurrentWeatherResponse
import com.rmtz.weather.models.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appId: String = Const.api
    ): Response<CurrentWeatherResponse>

    @GET("/geo/1.0/direct")
    suspend fun getGeoLocation(
        @Query("q") q: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") appId: String = Const.api
    ): Response<LocationResponse>
}