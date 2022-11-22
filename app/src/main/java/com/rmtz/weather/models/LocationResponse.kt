package com.rmtz.weather.models
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


class LocationResponse : ArrayList<LocationResponse.LocationResponseItem>(){
    @Keep
    data class LocationResponseItem(
        @SerializedName("country")
        val country: String, // ID
        @SerializedName("lat")
        val lat: Double, // -7.7674666000000006
        @SerializedName("lon")
        val lon: Double, // 110.29605014348246
        @SerializedName("name")
        val name: String, // Godean
        @SerializedName("state")
        val state: String // Special Region of Yogyakarta
    )
}