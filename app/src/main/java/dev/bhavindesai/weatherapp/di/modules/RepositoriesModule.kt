package dev.bhavindesai.weatherapp.di.modules

import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.local.WeatherDataDao
import dev.bhavindesai.data.repositories.WeatherRepository
import dev.bhavindesai.data.remote.WeatherService
import dev.bhavindesai.data.utils.InternetUtil
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @[Provides Singleton]
    fun providesWeatherRepository(
        weatherService: WeatherService,
        weatherDataDao: WeatherDataDao,
        internetUtil: InternetUtil
    ) = WeatherRepository(weatherService, weatherDataDao, internetUtil)
}