package com.agaperra.weatherforecast.domain.model

sealed class AppState<T>(val data: T? = null, val message: ErrorState? = null) {
    class Success<T>(data: T): AppState<T>(data)
    class Error<T>(error: ErrorState, data: T? = null): AppState<T>(data, error)
    class Loading<T>(data: T? = null): AppState<T>(data)
}