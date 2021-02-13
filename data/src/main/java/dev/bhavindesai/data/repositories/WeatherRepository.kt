package dev.bhavindesai.data.repositories

import dev.bhavindesai.data.local.WeatherDataDao
import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.sources.MultiDataSource
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.domain.local.Location
import dev.bhavindesai.domain.local.LocationWeatherData
import dev.bhavindesai.domain.local.Weather
import dev.bhavindesai.domain.remote.LocationResponse
import dev.bhavindesai.domain.remote.WeatherResponse
import dev.bhavindesai.domain.remote.WhereOnEarth
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WeatherRepository(
    private val weatherService: WeatherService,
    private val weatherDataDao: WeatherDataDao,
) {

    @FlowPreview
    fun getWeatherForCity(city: String) =
        rdsWhereOnEarth.getRemoteData(city)
            .map { it.first() }
            .flatMapConcat { mdsWeather.fetch(it.woeid) }

    private val rdsWhereOnEarth = object : RemoteDataSource<String, List<WhereOnEarth>> {
        override fun getRemoteData(requestData: String) = flow {
            emit(weatherService.getWhereOnEarth(requestData))
        }
    }

    private val mdsWeather = object : MultiDataSource<LocationWeatherData, Long, LocationResponse>() {
        override fun mapper(remoteData: LocationResponse): LocationWeatherData {
            return remoteData.toLocationWeatherData()
        }

        override suspend fun getLocalData(): LocationWeatherData = weatherDataDao.getLocation()

        override suspend fun storeLocalData(data: LocationWeatherData) {
            weatherDataDao.deleteAllLocations()
            weatherDataDao.storeLocation(data.locationData)

            weatherDataDao.deleteAllWeathers()
            weatherDataDao.storeWeather(data.weatherData)
        }

        override fun getRemoteData(requestData: Long) = flow {
            emit(weatherService.getWeatherData(requestData))
        }
    }
}

private fun LocationResponse.toLocationWeatherData(): LocationWeatherData = LocationWeatherData(
    Location(time, sun_rise, sun_set, title, woeid),
    consolidated_weather.map {
        it.toWeather(woeid)
    }
)

private fun WeatherResponse.toWeather(woeid: Long) = Weather(
    id,
    woeid,
    weather_state_name,
    weather_state_abbr,
    wind_direction_compass,
    created,
    applicable_date,
    min_temp,
    max_temp,
    the_temp,
    wind_speed,
    wind_direction,
    air_pressure,
    humidity,
    visibility,
    predictability
)
