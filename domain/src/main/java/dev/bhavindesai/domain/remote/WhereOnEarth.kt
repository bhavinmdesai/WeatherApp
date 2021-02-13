package dev.bhavindesai.domain.remote

data class WhereOnEarth (
    val title: String,
    val locationType: String,
    val woeid: Long,
    val lattLong: String
)