package dev.bhavindesai.domain.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WhereOnEarth (
    val title: String,
    val location_type: String,
    @PrimaryKey
    val woeid: Long,
    val latt_long: String
)