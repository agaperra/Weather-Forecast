package com.agaperra.weatherforecast.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_table")
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "city_latitude")
    val latitude: Double,
    @ColumnInfo(name = "city_longitude")
    val longitude: Double
)