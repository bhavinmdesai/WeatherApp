package dev.bhavindesai.data.remote

import dev.bhavindesai.domain.Location
import dev.bhavindesai.domain.WhereOnEarth
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("location/search")
    suspend fun getWhereOnEarth(@Query("query") city: String) : List<WhereOnEarth>

    @GET("location/{woeId}")
    fun getWeatherData(@Path("woeId") woeId: Long) : List<Location>

}