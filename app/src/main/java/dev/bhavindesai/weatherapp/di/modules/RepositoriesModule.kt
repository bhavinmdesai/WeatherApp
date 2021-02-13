package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.data.remote.WeatherService
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @[Provides Singleton]
    fun providesWeatherRepository(weatherService: WeatherService) = WeatherRepository(weatherService)
}