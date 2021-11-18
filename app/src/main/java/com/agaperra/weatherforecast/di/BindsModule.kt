package com.agaperra.weatherforecast.di

import com.agaperra.weatherforecast.data.repository.DataStoreRepositoryImpl
import com.agaperra.weatherforecast.data.repository.ForecastRepositoryImpl
import com.agaperra.weatherforecast.domain.interactor.WeatherIconsInteractor
import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import com.agaperra.weatherforecast.domain.repository.ForecastRepository
import com.agaperra.weatherforecast.presentation.interactor.WeatherIconsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
interface BindsModule {

    @Binds
    fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository

    @Binds
    fun bindIconsInteractor(weatherIconsInteractorImpl: WeatherIconsInteractorImpl): WeatherIconsInteractor

}