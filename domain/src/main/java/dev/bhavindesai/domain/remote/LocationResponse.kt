package dev.bhavindesai.domain.remote

data class LocationResponse (
    val consolidated_weather: List<WeatherResponse>,
    val time: String,
    val sun_rise: String,
    val sun_set: String,
    val title: String,
    val woeid: Long,
)

data class WeatherResponse (
    val id: Long,
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