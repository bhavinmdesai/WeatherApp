package dev.bhavindesai.data.repositories

import dev.bhavindesai.data.local.WeatherDataDao
import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.sources.MultiDataSource
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.data.utils.InternetUtil
import dev.bhavindesai.domain.local.Location
import dev.bhavindesai.domain.local.LocationWeatherData
import dev.bhavindesai.domain.local.Weather
import dev.bhavindesai.domain.remote.LocationResponse
import dev.bhavindesai.domain.remote.WeatherOfTheDayRequest
import dev.bhavindesai.domain.remote.WeatherResponse
import dev.bhavindesai.domain.remote.WhereOnEarth
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class WeatherRepository(
    private val weatherService: WeatherService,
    private val weatherDataDao: WeatherDataDao,
) {

    @FlowPreview
    fun getWeatherForCity(city: String) =
        mdsWhereOnEarth.fetch(city)
            .flatMapConcat {
                if (it != null) {
                    mdsWeather.fetch(it.woeid)
                } else {
                    flowOf(null)
                }
            }

    fun getWeatherOfCityForTheDay(woeId: Long, year: Int, month: Int, date: Int) : Flow<List<WeatherResponse>?> {
        return if (InternetUtil.isInternetOn()) {
            rdsWeatherOfTheDay.getRemoteData(WeatherOfTheDayRequest(woeId, year, month, date))
        } else {
            flowOf(null)
        }
    }

    private val rdsWeatherOfTheDay = object : RemoteDataSource<WeatherOfTheDayRequest, List<WeatherResponse>> {
        override fun getRemoteData(requestData: WeatherOfTheDayRequest) = flow {
            emit(weatherService.getWeatherOfTheDay(
                requestData.woeId,
                requestData.year,
                requestData.month,
                requestData.date,
            ))
        }
    }


    private val mdsWeather = object : MultiDataSource<LocationWeatherData?, Long, LocationResponse>() {
        override fun mapper(remoteData: LocationResponse): LocationWeatherData {
            return remoteData.toLocationWeatherData()
        }

        override suspend fun getLocalData(): LocationWeatherData? =
            weatherDataDao.getLocation()?.apply {
                weatherData = weatherData.sortedBy { it.applicable_date }
            }

        override suspend fun storeLocalData(data: LocationWeatherData?) {
            data?.let {
                weatherDataDao.deleteAllLocations()
                weatherDataDao.storeLocation(data.locationData)

                weatherDataDao.deleteAllWeathers()
                weatherDataDao.storeWeather(data.weatherData)
            }
        }

        override fun getRemoteData(requestData: Long) = flow {
            emit(weatherService.getWeatherData(requestData))
        }
    }

    private val mdsWhereOnEarth = object : MultiDataSource<WhereOnEarth?, String, List<WhereOnEarth>>() {
        override fun mapper(remoteData: List<WhereOnEarth>): WhereOnEarth = remoteData.first()

        override suspend fun getLocalData() = weatherDataDao.getWhereOnEarth()

        override suspend fun storeLocalData(data: WhereOnEarth?) {
            data?.let {
                weatherDataDao.deleteAllWhereOnEarth()
                weatherDataDao.storeWhereOnEarth(data)
            }
        }

        override fun getRemoteData(requestData: String) = flow {
            emit(weatherService.getWhereOnEarth(requestData))
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
