package com.rmtz.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "temperature_unit") val tempUnit: String,
    @ColumnInfo(name = "location_id") val locationId: Long,
    @ColumnInfo(name = "location_name") val locationName: String,
)