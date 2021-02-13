package dev.bhavindesai.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.domain.remote.WeatherResponse
import javax.inject.Inject

class WeatherDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun fetchWeatherDetailsOfDay(woeId: Long, date: String): LiveData<List<WeatherResponse>?> {
        val parts = date.split("-")

        return weatherRepository.getWeatherOfCityForTheDay(
            woeId,
            parts[0].toInt(),
            parts[1].toInt(),
            parts[2].toInt()
        ).asLiveData()
    }
}