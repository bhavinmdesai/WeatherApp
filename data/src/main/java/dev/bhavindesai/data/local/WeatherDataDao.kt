package dev.bhavindesai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.bhavindesai.domain.local.Location
import dev.bhavindesai.domain.local.LocationWeatherData
import dev.bhavindesai.domain.local.Weather

@Dao
interface WeatherDataDao {

    @Transaction
    @Query("SELECT * FROM Location")
    suspend fun getLocation() : LocationWeatherData

    @Insert
    suspend fun storeLocation(location: Location)

    @Insert
    suspend fun storeWeather(location: List<Weather>)

    @Query("DELETE FROM Location")
    suspend fun deleteAllLocations()

    @Query("DELETE FROM Weather")
    suspend fun deleteAllWeathers()
}