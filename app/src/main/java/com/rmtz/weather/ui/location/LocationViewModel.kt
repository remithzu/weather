package com.rmtz.weather.ui.location

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rmtz.weather.app.WeatherApplication
import com.rmtz.weather.models.CurrentWeatherResponse
import com.rmtz.weather.models.LocationResponse
import com.rmtz.weather.networks.NetworkState
import com.rmtz.weather.repository.WeatherRepository
import com.rmtz.weather.utils.Utils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LocationViewModel(application: Application, private val repository: WeatherRepository) : AndroidViewModel(application) {
    private var job: Job? = null
    private val _geoLocation = MutableLiveData<NetworkState<LocationResponse>>()
    val geoLocation: LiveData<NetworkState<LocationResponse>> = _geoLocation

    fun getGeoLocation(location: String) {
        val wApplication = getApplication<WeatherApplication>()
        Log.d("wLog", Thread.currentThread().name)
        _geoLocation.postValue(NetworkState.Loading())
        viewModelScope.launch {
            try {
                if (Utils.isConnect(wApplication)) {
                    val response = repository.getGeoLocation(location)
                    if (response.isSuccessful) {
                        response.body().let {
                            _geoLocation.postValue(NetworkState.Success(it!!))
                        }
                    }
                }
            } catch (t: Throwable) {
                _geoLocation.postValue(NetworkState.Error(t))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}