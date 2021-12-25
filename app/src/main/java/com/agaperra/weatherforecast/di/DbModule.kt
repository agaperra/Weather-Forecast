package com.agaperra.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.agaperra.weatherforecast.data.db.dao.CitiesDao
import com.agaperra.weatherforecast.data.db.CitiesDatabase
import com.agaperra.weatherforecast.domain.util.Constants.CITIES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideCitiesDatabase(@ApplicationContext context: Context): CitiesDatabase = Room
        .databaseBuilder(context, CitiesDatabase::class.java, CITIES_DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideCitiesDao(db: CitiesDatabase): CitiesDao = db.citiesDao()

}