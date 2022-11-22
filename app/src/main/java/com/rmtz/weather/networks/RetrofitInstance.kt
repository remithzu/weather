package com.rmtz.weather.networks

import com.rmtz.weather.commons.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        var openWeatherApi: OpenWeatherApi? = null
        private val client = OkHttpClient.Builder().build()

        fun getInstance() : OpenWeatherApi {
            if (openWeatherApi == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Const.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
            }
            return openWeatherApi!!
        }
    }
}