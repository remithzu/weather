package com.rmtz.weather.ui.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rmtz.weather.app.WeatherApplication
import com.rmtz.weather.models.CurrentWeatherResponse
import com.rmtz.weather.networks.NetworkState
import com.rmtz.weather.repository.WeatherRepository
import com.rmtz.weather.utils.Utils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val repository: WeatherRepository) : AndroidViewModel(application) {
    private var job: Job? = null
    private val _currentWeather = MutableLiveData<NetworkState<CurrentWeatherResponse>>()
    val currentWeather: LiveData<NetworkState<CurrentWeatherResponse>> = _currentWeather

    fun getCurrentWeather(location: String) {
        val wApplication = getApplication<WeatherApplication>()
        Log.d("wLog", Thread.currentThread().name)
        _currentWeather.postValue(NetworkState.Loading())
        viewModelScope.launch {
            try {
                if (Utils.isConnect(wApplication)) {
                    val response = repository.getCurrentWeather(location)
                    if (response.isSuccessful) {
                        response.body().let {
                            _currentWeather.postValue(NetworkState.Success(it!!))
                        }
                    }
                }
            } catch (t: Throwable) {
                _currentWeather.postValue(NetworkState.Error(t))
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}