package com.agaperra.weatherforecast.data.state

import ForecastResponse


sealed class AppState {

    data class Success(val data:  ForecastResponse) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()

}
