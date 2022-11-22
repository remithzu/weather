package com.rmtz.weather.networks


sealed class NetworkState<T>(
    val data: T? = null,
    val message: Throwable? = null
    ) {
    class Success<T>(data: T) : NetworkState<T>(data)
    class Error<T>(message: Throwable, data: T? = null) : NetworkState<T>(data, message)
    class Loading<T> : NetworkState<T>()
}