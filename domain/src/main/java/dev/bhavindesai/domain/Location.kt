package dev.bhavindesai.domain

data class Location (
    val consolidatedWeather: List<ConsolidatedWeather>,
    val time: String,
    val sunRise: String,
    val sunSet: String,
    val timezoneName: String,
    val parent: Parent,
    val sources: List<Source>,
    val title: String,
    val locationType: String,
    val woeid: Long,
    val lattLong: String,
    val timezone: String
)

data class ConsolidatedWeather (
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

data class Parent (
    val title: String,
    val locationType: String,
    val woeid: Long,
    val lattLong: String
)

data class Source (
    val title: String,
    val slug: String,
    val url: String,
    val crawlRate: Long
)
