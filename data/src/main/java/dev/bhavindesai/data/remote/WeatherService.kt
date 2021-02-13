package dev.bhavindesai.data.remote

import dev.bhavindesai.domain.Location
import dev.bhavindesai.domain.WhereOnEarth
import retrofit2.http.GET

interface WeatherService {

    @GET
    fun getWhereOnEarth(city: String) : List<WhereOnEarth>

    @GET
    fun getWeatherData(woeId: Long) : List<Location>

}