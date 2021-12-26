package com.agaperra.weatherforecast.data.db.dao


import androidx.room.*
import com.agaperra.weatherforecast.data.db.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity: CityEntity)

    @Delete()
    suspend fun deleteCity(cityEntity: CityEntity)

    @Query(value = "SELECT * FROM cities_table")
    fun getAllCities(): Flow<List<CityEntity>>

    @Query(value = "SELECT * FROM cities_table WHERE city_name = :cityName")
    fun searchCities(cityName: String): Flow<List<CityEntity>>

}