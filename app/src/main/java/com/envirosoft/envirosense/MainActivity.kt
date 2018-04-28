package com.envirosoft.envirosense

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

import com.envirosoft.envirosense.data.AppCollections
import com.envirosoft.envirosense.model.EnvironmentDataEntry
import com.envirosoft.envirosense.service.JsonFileReader
import com.envirosoft.envirosense.service.JsonFileSaver
import com.envirosoft.envirosense.service.LocationService
import com.envirosoft.envirosense.service.SimpleSensorListener

import java.io.FileOutputStream
import java.util.ArrayList

import org.json.JSONException
import java.io.IOException

class MainActivity : AppCompatActivity() {
  private lateinit var sensorManager: SensorManager
  private lateinit var pressureSensor: Sensor
  private lateinit var lightSensor: Sensor
  private var temperatureSensor: Sensor? = null
  private lateinit var pressureListener: SimpleSensorListener
  private lateinit var lightListener: SimpleSensorListener
  private  var temperatureListener: SimpleSensorListener? = null
  private lateinit var pressureView: TextView
  private lateinit var lightView: TextView
  private lateinit var temperatureView: TextView
  private lateinit var locationView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    try {
      val fileInputStream = openFileInput("data.json")
      AppCollections.entryList = JsonFileReader.getEntriesFromJson(fileInputStream)
    } catch (e: IOException) {
      AppCollections.entryList = ArrayList()
    } catch (e: JSONException) {
      e.printStackTrace()
    }

    pressureView = findViewById(R.id.pressureView)
    lightView = findViewById(R.id.lightView)
    temperatureView = findViewById(R.id.temperatureView)
    locationView = findViewById(R.id.locationView)
    val locationService = LocationService(applicationContext, locationView)

    startSensorListeners()
    findViewById<Button>(R.id.graphBtn).setOnClickListener { checkForAvailableData() }
    findViewById<Button>(R.id.saveBtn).setOnClickListener { saveData() }
  }

  /**
   * Check if there is any data.
   */
  private fun checkForAvailableData() {
    // Only allow to open graph if there is data saved
    if (AppCollections.entryList.size > 2) {
      openGraphWindow()
    } else {
      Snackbar.make(findViewById(R.id.mainLayout), R.string.not_enough_data, Snackbar.LENGTH_SHORT)
        .show()
    }
  }

  private fun startSensorListeners() {
    // Init sensor manager to access sensors
    sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // get pressure sensor and use listener to add to textview
    pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    pressureListener = SimpleSensorListener(pressureView, "hpa", "Pressure")

    // get light sensor and use listener to add to textview
    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    lightListener = SimpleSensorListener(lightView, "lx", "Light")

    temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    // Test if ambient temperature sensor is available because only a few devices have it
    temperatureListener = SimpleSensorListener(temperatureView, "°C", "Temperature")
  }

  override fun onResume() {
    // Register a listener for the sensor.
    super.onResume()

    sensorManager.registerListener(
      pressureListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL
    )
    sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    sensorManager.registerListener(
      temperatureListener,
      temperatureSensor,
      SensorManager.SENSOR_DELAY_NORMAL
    )
  }

  override fun onPause() {
    // Be sure to unregister the sensor when the activity pauses.
    super.onPause()
    sensorManager.unregisterListener(pressureListener)
    sensorManager.unregisterListener(lightListener)
    sensorManager.unregisterListener(temperatureListener)
  }


  /**
   * Instantiate an Intent to open the graph activity.
   */
  private fun openGraphWindow() {
    startActivity(Intent(this, GraphActivity::class.java))
  }

  /**
   * Save data to data.json.
   */
  private fun saveData() {
    val filename = "data.json"
    val outputStream: FileOutputStream
    val pressure = pressureListener.value
    val light = lightListener.value

    AppCollections.entryList.add(
      EnvironmentDataEntry(
        EnvironmentDataEntry.LOCATION_UNKNOWN,
        pressure!!,
        light!!,
        temperatureListener?.value ?: 0f // Temperatur listener is not available on  most devices.
      )
    )
    try {
      outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
      JsonFileSaver.saveToFileStream(outputStream, AppCollections.entryList)
    } catch ( e: Exception) {
      e.printStackTrace()
    }
  }
}
