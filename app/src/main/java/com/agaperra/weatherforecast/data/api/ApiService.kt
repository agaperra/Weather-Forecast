package com.agaperra.weatherforecast.data.api

import com.agaperra.weatherforecast.data.repository.ForecastRepository
import com.agaperra.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiService {

    @Singleton
    @Provides
    fun providesApiInterface(): ApiInterface {

        var okHttpClient: OkHttpClient? = null
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.WEATHER_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideForecastRepository(
        api: ApiInterface
    ) = ForecastRepository(api)

}
