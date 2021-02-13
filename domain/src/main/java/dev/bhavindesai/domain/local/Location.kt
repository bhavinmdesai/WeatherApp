package dev.bhavindesai.domain.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class LocationWeatherData(
    @Embedded
    val locationData: Location,
    @Relation(
        parentColumn = "woeid",
        entityColumn = "woeid"
    )
    var weatherData: List<Weather>
)

@Entity
data class Location (
    val time: String,
    val sunRise: String,
    val sunSet: String,
    val title: String,
    @PrimaryKey
    val woeid: Long,
)

@Entity
data class Weather (
    @PrimaryKey
    val id: Long,
    val woeid: Long,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_direction_compass: String,
    val created: String,
    val applicable_date: String,
    val min_temp: Double,
    val max_temp: Double,
    val the_temp: Double,
    val wind_speed: Double,
    val wind_direction: Double,
    val air_pressure: Double,
    val humidity: Long,
    val visibility: Double,
    val predictability: Long
)
