package dev.bhavindesai.domain

typealias WhereOnEarthList = ArrayList<WhereOnEarth>

data class WhereOnEarth (
    val title: String,
    val locationType: String,
    val woeid: Long,
    val lattLong: String
)