package com.rmtz.weather.models
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class CurrentWeatherResponse(
    @SerializedName("base")
    val base: String, // stations
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int, // 200
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Long, // 1668849837
    @SerializedName("id")
    val id: Int, // 1643981
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String, // Godean
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Long, // 25200
    @SerializedName("visibility")
    val visibility: Int, // 10000
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    @Keep
    data class Clouds(
        @SerializedName("all")
        val all: Int // 100
    )

    @Keep
    data class Coord(
        @SerializedName("lat")
        val lat: Double, // -7.7697
        @SerializedName("lon")
        val lon: Double // 110.2939
    )

    @Keep
    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double, // 303.79
        @SerializedName("grnd_level")
        val grndLevel: Int, // 992
        @SerializedName("humidity")
        val humidity: Int, // 75
        @SerializedName("pressure")
        val pressure: Int, // 1005
        @SerializedName("sea_level")
        val seaLevel: Int, // 1005
        @SerializedName("temp")
        val temp: Double, // 300.82
        @SerializedName("temp_max")
        val tempMax: Double, // 300.82
        @SerializedName("temp_min")
        val tempMin: Double // 300.82
    )

    @Keep
    data class Sys(
        @SerializedName("country")
        val country: String, // ID
        @SerializedName("sunrise")
        val sunrise: Long, // 1668809382
        @SerializedName("sunset")
        val sunset: Long // 1668854329
    )

    @Keep
    data class Weather(
        @SerializedName("description")
        val description: String, // overcast clouds
        @SerializedName("icon")
        val icon: String, // 04d
        @SerializedName("id")
        val id: Int, // 804
        @SerializedName("main")
        val main: String // Clouds
    )

    @Keep
    data class Wind(
        @SerializedName("deg")
        val deg: Int, // 222
        @SerializedName("gust")
        val gust: Double, // 1.25
        @SerializedName("speed")
        val speed: Double // 1.36
    )
}