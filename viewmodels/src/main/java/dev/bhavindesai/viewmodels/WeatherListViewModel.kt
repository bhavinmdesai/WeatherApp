package dev.bhavindesai.viewmodels

import androidx.lifecycle.*
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.domain.local.LocationWeatherData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val mldWeatherForecast = MutableLiveData<LocationWeatherData?>()
    val weatherForecast: LiveData<LocationWeatherData?> = mldWeatherForecast

    @FlowPreview
    fun fetchWeather() {
        viewModelScope.launch {
            weatherRepository.getWeatherForCity("london").collect {
                mldWeatherForecast.value = it
            }
        }
    }
}