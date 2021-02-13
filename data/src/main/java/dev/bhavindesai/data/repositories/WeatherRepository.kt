package dev.bhavindesai.data.repositories

import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.sources.MultiDataSource
import dev.bhavindesai.data.sources.RemoteDataSource
import dev.bhavindesai.domain.Location
import dev.bhavindesai.domain.WhereOnEarth
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WeatherRepository(
    private val weatherService: WeatherService
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

        override fun getRemoteData(requestData: Long) = flow {
            emit(weatherService.getWeatherData(requestData))
        }
    }
}