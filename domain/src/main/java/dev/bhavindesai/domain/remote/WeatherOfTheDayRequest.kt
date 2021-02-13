package dev.bhavindesai.domain.remote

data class WeatherOfTheDayRequest(
    val woeId: Long,
    val year: Int,
    val month: Int,
    val date: Int,
)
