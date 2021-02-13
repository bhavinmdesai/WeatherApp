package dev.bhavindesai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.bhavindesai.domain.local.Location
import dev.bhavindesai.domain.local.Weather

@Database(entities = [Location::class, Weather::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao
}