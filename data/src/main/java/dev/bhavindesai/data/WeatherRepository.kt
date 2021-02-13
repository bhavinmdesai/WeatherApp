package dev.bhavindesai.data

import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.sources.MultiDataSource
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.domain.Location
import dev.bhavindesai.domain.WhereOnEarth
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherForCity(city: String) : Flow<Location> {
        val whereOnEarthList = getWhereOnEarthId(city)

        return getWeatherForWoeId(whereOnEarthList.first().woeid)
    }

    private suspend fun getWhereOnEarthId(city: String) = rdsWhereOnEarth.getRemoteData(city)

    private fun getWeatherForWoeId(woeId: Long) = mdsWeather.fetch(woeId)

    private val rdsWhereOnEarth = object : RemoteDataSource<String, List<WhereOnEarth>> {
        override suspend fun getRemoteData(
            requestData: String
        ): List<WhereOnEarth> = weatherService.getWhereOnEarth(requestData)
    }

    private val mdsWeather = object : MultiDataSource<Location, Long, List<Location>>() {
        override fun mapper(remoteData: List<Location>): Location {
            return remoteData.first()
        }

        override suspend fun getLocalData(): Location {
            TODO("Not yet implemented")
        }

        override suspend fun storeLocalData(data: Location) {
            TODO("Not yet implemented")
        }

        override suspend fun getRemoteData(requestData: Long): List<Location> =
            weatherService.getWeatherData(requestData)
    }
}