package com.envirosoft.envirosense.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.widget.TextView


class SimpleSensorListener(
  private val view: TextView,
  private val measurement: String,
  private val sensorType: String
) : SensorEventListener {
   var value: Float? = null

  override fun onSensorChanged(event: SensorEvent?) {
    value = event!!.values[0]
    view.text = "$sensorType: $value $measurement"
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

  }
}
