package dev.bhavindesai.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.domain.Location
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(
    weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherForecast = weatherRepository
        .getWeatherForCity("london")
        .asLiveData(viewModelScope.coroutineContext)

    val weatherForecast: LiveData<Location>
        get() = _weatherForecast
}