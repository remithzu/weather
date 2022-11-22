package com.rmtz.weather.commons

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.rmtz.weather.R
import java.text.SimpleDateFormat
import java.util.*

object Weather {
    fun getIcon(code: String): String {
        return "http://openweathermap.org/img/wn/$code@2x.png"
    }

    fun getName(code: String): String {
        return when(code) {
            "01d","01n" -> "Clear sky"
            "02d","02n" -> "Few clouds"
            "03d","03n" -> "Scattered clouds"
            "04d","04n" -> "Broken clouds"
            "09d","09n" -> "Shower rain"
            "10d","10n" -> "Rain"
            "11d","11n" -> "Thunderstorm"
            "13d","13n" -> "Snow"
            "50d","50n" -> "Mist"
            else -> ""
        }
    }

    fun Context.getBackground(code: String): Drawable {
        return when(code) {
            "01d","01n" -> ContextCompat.getDrawable(this, R.drawable.draw_clear_sky)!!
            "02d","02n" -> ContextCompat.getDrawable(this, R.drawable.draw_few_clouds)!!
            "03d","03n" -> ContextCompat.getDrawable(this, R.drawable.draw_scattered_clouds)!!
            "04d","04n" -> ContextCompat.getDrawable(this, R.drawable.draw_broken_clouds)!!
            "09d","09n" -> ContextCompat.getDrawable(this, R.drawable.draw_shower_rain)!!
            "10d","10n" -> ContextCompat.getDrawable(this, R.drawable.draw_rain)!!
            "11d","11n" -> ContextCompat.getDrawable(this, R.drawable.draw_thunderstorm)!!
            "13d","13n" -> ContextCompat.getDrawable(this, R.drawable.draw_snow)!!
            "50d","50n" -> ContextCompat.getDrawable(this, R.drawable.draw_mist)!!
            else -> ContextCompat.getDrawable(this, R.drawable.draw_white)!!
        }
    }

    fun Long.toFormatDate(f: String): String {
        val date = Date(this * 1000)
        val simpleDateFormat = SimpleDateFormat(f, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun Long.toTimezone(): Int {
        val tz = TimeZone.getDefault()
        val now = Date()
        return tz.getOffset(now.time) / 1000
    }
}