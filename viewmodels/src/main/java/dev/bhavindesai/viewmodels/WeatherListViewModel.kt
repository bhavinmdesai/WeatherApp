package dev.bhavindesai.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.bhavindesai.data.repositories.WeatherRepository
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(
    weatherRepository: WeatherRepository
) : ViewModel() {

    @FlowPreview
    val weatherForecast = weatherRepository
        .getWeatherForCity("london")
        .asLiveData(viewModelScope.coroutineContext)
}