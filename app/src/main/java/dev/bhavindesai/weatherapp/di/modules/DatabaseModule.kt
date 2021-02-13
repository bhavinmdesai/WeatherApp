package dev.bhavindesai.weatherapp.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.bhavindesai.data.local.WeatherDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @[Provides Singleton]
    fun provideWeatherDatabase(application: Application) = Room.databaseBuilder(
        application.applicationContext,
        WeatherDatabase::class.java,
        "weather_database"
    ).build()

    @[Provides Singleton]
    fun providesWeatherDataDao(weatherDatabase: WeatherDatabase) = weatherDatabase.weatherDataDao()
}