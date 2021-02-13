package dev.bhavindesai.domain

data class WhereOnEarth (
    val title: String,
    val locationType: String,
    val woeid: Long,
    val lattLong: String
)