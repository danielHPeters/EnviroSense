package com.envirosoft.envirosense.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Service to get latest location data.
 *
 * @author Daniel Peters
 * @version: 1.2
 */
class LocationService : Service() {
  override fun onBind(intent: Intent): IBinder {
    TODO("Return the communication channel to the service.")
  }
}
