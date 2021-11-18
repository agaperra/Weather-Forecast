package com.agaperra.weatherforecast.data.network

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}