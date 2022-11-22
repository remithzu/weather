package com.rmtz.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.rmtz.weather.app.WeatherApplication
import java.text.DecimalFormat
import kotlin.math.min

object Utils {
    fun isConnect(application: WeatherApplication): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
        }
    }

    fun Double.toFormat(): String {
        val df = DecimalFormat("#.#")
        return (df.format(this))
    }

    fun Double.setTempConversion(unit: String): String {
        Log.e("wLog", "unit:: $unit")
        return when(unit) {
            "Kelvin" -> {
                "$this°K"
            }
            "Celsius" -> {
                "${
                    (this - 273.15).toFormat()
                }°C"
            }
            "Fahrenheit" -> {
                "${
                    ((this - 273.15) * 9/5 + 32).toFormat()
                }°F"
            }
            else -> {this.toString()}
        }
    }
}