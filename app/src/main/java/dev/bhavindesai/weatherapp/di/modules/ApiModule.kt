package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.remote.WeatherService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @[Provides Singleton]
    fun providesWeatherService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)
}
