package dev.bhavindesai.data.remote

import dev.bhavindesai.domain.remote.LocationResponse
import dev.bhavindesai.domain.remote.WhereOnEarth
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("location/search")
    suspend fun getWhereOnEarth(@Query("query") city: String) : List<WhereOnEarth>

    @GET("location/{woeId}")
    suspend fun getWeatherData(@Path("woeId") woeId: Long) : LocationResponse

}