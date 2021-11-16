package com.agaperra.weatherforecast.di

import android.content.Context
import com.agaperra.weatherforecast.data.api.ForecastApi
import com.agaperra.weatherforecast.data.network.NetworkStatusListener
import com.agaperra.weatherforecast.domain.util.Constants.WEATHER_API_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient): ForecastApi = Retrofit.Builder()
        .client(client)
        .baseUrl(WEATHER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()
        .create(ForecastApi::class.java)

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideNetworkStatusListener(@ApplicationContext context: Context): NetworkStatusListener =
        NetworkStatusListener(context)

}
