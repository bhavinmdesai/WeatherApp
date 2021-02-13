package dev.bhavindesai.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bhavindesai.data.WeatherRepository
import kotlinx.coroutines.launch

class WeatherListViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun fetchWeatherData() {
        viewModelScope.launch {
            weatherRepository.getWeatherForCity("london")
        }
    }
}