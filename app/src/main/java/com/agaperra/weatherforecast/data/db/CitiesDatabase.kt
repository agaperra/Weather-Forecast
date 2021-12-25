package com.agaperra.weatherforecast.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agaperra.weatherforecast.data.db.dao.CitiesDao
import com.agaperra.weatherforecast.data.db.entities.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

}