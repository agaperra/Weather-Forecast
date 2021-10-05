package com.agaperra.weatherforecast.utils

sealed class AppState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): AppState<T>(data)
    class Error<T>(message: String, data: T? = null): AppState<T>(data, message)
    class Loading<T>(data: T? = null): AppState<T>(data)
}