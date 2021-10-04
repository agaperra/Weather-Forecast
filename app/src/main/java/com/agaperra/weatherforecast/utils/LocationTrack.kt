package com.agaperra.weatherforecast.utils

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.agaperra.weatherforecast.utils.LocationTrack
import android.content.Intent
import android.location.Location
import android.os.IBinder
import com.agaperra.weatherforecast.utils.Constants.MINIMUM_DISTANCE_FOR_UPDATES
import com.agaperra.weatherforecast.utils.Constants.MINIMUM_TIME_BETWEEN_UPDATES
import com.google.android.gms.location.LocationListener
import java.lang.Exception
import android.content.DialogInterface
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog


open class LocationTrack(private val mContext: Context) : LocationListener {
    var checkGPS = false
    var checkNetwork = false
    var canGetLocation = false
    var loc: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0
    protected var locationManager: LocationManager? = null

    private val location: Location?
        get() {
            try {
                locationManager = mContext
                    .getSystemService(LOCATION_SERVICE) as LocationManager

                checkGPS = locationManager!!
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)

                checkNetwork = locationManager!!
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (!checkGPS && !checkNetwork) {
                    Toast.makeText(mContext, "No Service Provider is available", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    canGetLocation = true

                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MINIMUM_TIME_BETWEEN_UPDATES,
                            MINIMUM_DISTANCE_FOR_UPDATES,
                            this as android.location.LocationListener
                        )
                        if (locationManager != null) {
                            loc = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (loc != null) {
                                latitude = loc!!.latitude
                                longitude = loc!!.longitude
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return loc
        }

    fun getLongitude(): Double {
        if (loc != null) {
            longitude = loc!!.longitude
        }
        return longitude
    }

    fun getLatitude(): Double {
        if (loc != null) {
            latitude = loc!!.latitude
        }
        return latitude
    }

    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    fun stopListener() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    mContext, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager!!.removeUpdates(this as android.location.LocationListener)
        }
    }

    override fun onLocationChanged(location: Location) {

    }

    init {
        location
    }
}