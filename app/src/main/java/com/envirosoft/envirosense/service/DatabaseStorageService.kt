package com.envirosoft.envirosense.service

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

object DatabaseStorageService {
  fun isConnected(context: Context): Boolean {
    val connMgr = context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
  }
}
