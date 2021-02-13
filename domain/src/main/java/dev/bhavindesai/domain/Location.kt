package dev.bhavindesai.domain

import androidx.room.Entity

@Entity(tableName = "Location")
data class Location (
    val consolidatedWeather: List<WeatherData>,
    val time: String,
    val sunRise: String,
    val sunSet: String,
    val title: String,
    val woeid: Long,
)

@Entity(tableName = "WeatherData")
data class WeatherData (
    val id: Long,
    val weatherStateName: String,
    val weatherStateAbbr: String,
    val windDirectionCompass: String,
    val created: String,
    val applicableDate: String,
    val minTemp: Double,
    val maxTemp: Double,
    val theTemp: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val airPressure: Double,
    val humidity: Long,
    val visibility: Double,
    val predictability: Long
)
