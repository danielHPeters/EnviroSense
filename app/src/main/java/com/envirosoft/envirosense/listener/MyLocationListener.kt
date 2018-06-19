package com.envirosoft.envirosense.listener

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView

/**
 * Location listener class.
 * TODO: Finish implementation.
 *
 * @author Daniel Peters
 * @version 1.2
 */
class MyLocationListener (context: Context, private val locationView: TextView) : LocationListener {
  companion object {
    const val MIN_UPDATE_DISTANCE: Float = 10f
    const val MIN_UPDATE_INTERVAL: Long = 60000L
  }

  var location: Location?
  private val locationManager: LocationManager =
    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
  private var gpsEnabled: Boolean
  private var networkEnabled: Boolean
  private var locationEnabled: Boolean

  override fun onLocationChanged(location: Location?) {
    locationView.text = location.toString()
  }

  override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    TODO("not implemented")
  }

  override fun onProviderEnabled(provider: String?) {
    TODO("not implemented")
  }

  override fun onProviderDisabled(provider: String?) {
    TODO("not implemented")
  }

  private fun initLocation() {
    try {
      if (gpsEnabled && networkEnabled) {
        locationEnabled = true
        // First get location from Network Provider
        locationManager.requestLocationUpdates(
          LocationManager.NETWORK_PROVIDER,
          MIN_UPDATE_INTERVAL,
          MIN_UPDATE_DISTANCE,
          this
        )
        Log.d("Network", "Network")
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
          locationView.text = location.toString()
        }
        // if GPS Enabled get lat/long using GPS Services
        if (gpsEnabled) {
          if (location == null) {
            locationManager.requestLocationUpdates(
              LocationManager.GPS_PROVIDER,
              MIN_UPDATE_INTERVAL,
              MIN_UPDATE_DISTANCE, this
            )
            Log.d("GPS Enabled", "GPS Enabled")
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationView.text = location.toString()
          }
        }
      }
    } catch (e: SecurityException) {
      e.printStackTrace()
    }
  }

  init {
    gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    locationEnabled = false
    location = null
  }
}
