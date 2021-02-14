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

    private val mldShowProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = mldShowProgress

    private val mldShowWeatherList = MutableLiveData<Boolean>()
    val showWeatherList: LiveData<Boolean> = mldShowWeatherList

    private val mldShowNoInternet = MutableLiveData<Boolean>()
    val showNoInternet: LiveData<Boolean> = mldShowNoInternet

    private val mldCityName = MutableLiveData<String>()
    val cityName: LiveData<String> = mldCityName

    @FlowPreview
    fun fetchWeather() {
        mldShowProgress.value = true
        mldShowWeatherList.value = false
        mldShowNoInternet.value = false
        mldCityName.value = ""

        viewModelScope.launch {
            weatherRepository.getWeatherForCity("london").collect {
                mldShowProgress.value = false
                mldWeatherForecast.value = it
                mldShowWeatherList.value = it != null
                mldShowNoInternet.value = it == null
                it?.let { notNullLocationWeatherData ->
                    mldCityName.value = notNullLocationWeatherData.locationData.title
                }
            }
        }
    }
}