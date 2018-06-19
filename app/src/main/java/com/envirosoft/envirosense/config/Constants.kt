package com.envirosoft.envirosense.config

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Application constant settings are defined here.
 *
 * @author Daniel Peters
 * @version 1.2
 */
object Constants {
  val DATE_FORMAT = SimpleDateFormat("hh:mm:ss", Locale.US)
  const val STORAGE_FILE = "data.json"
}
