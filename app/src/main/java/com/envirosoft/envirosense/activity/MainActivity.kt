package com.envirosoft.envirosense.activity

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.envirosoft.envirosense.R
import com.envirosoft.envirosense.config.Constants

import com.envirosoft.envirosense.data.AppCollections
import com.envirosoft.envirosense.interfaces.IDataHandler
import com.envirosoft.envirosense.model.EnvironmentDataEntry
import com.envirosoft.envirosense.listener.SimpleSensorListener
import com.envirosoft.envirosense.service.JsonFileHandler

import java.io.FileOutputStream

import java.io.IOException

/**
 * Main activity class. Displays sensor data.
 *
 * @author Daniel Peters
 * @version 1.2
 */
class MainActivity : AppCompatActivity() {
  private lateinit var sensorManager: SensorManager
  private lateinit var pressureSensor: Sensor
  private lateinit var lightSensor: Sensor
  private var temperatureSensor: Sensor? = null
  private lateinit var pressureListener: SimpleSensorListener
  private lateinit var lightListener: SimpleSensorListener
  private var temperatureListener: SimpleSensorListener? = null
  private lateinit var pressureView: TextView
  private lateinit var lightView: TextView
  private lateinit var temperatureView: TextView
  private lateinit var locationView: TextView
  private var jsonFileHandler: IDataHandler<EnvironmentDataEntry> = JsonFileHandler()

  /**
   * Set up the MainActivity view, start sensors and load already available data.
   *
   * @param savedInstanceState Instance state
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Sensor display views.
    pressureView = findViewById(R.id.pressureView)
    lightView = findViewById(R.id.lightView)
    temperatureView = findViewById(R.id.temperatureView)
    locationView = findViewById(R.id.locationView)

    // Buttons
    findViewById<Button>(R.id.graphBtn).setOnClickListener { checkForAvailableData() }
    findViewById<Button>(R.id.saveBtn).setOnClickListener { saveData() }

    // val locationService = LocationService(applicationContext, locationView)

    // Init sensor manager to access sensors
    sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    logAvailableSensors()
    startSensorListeners()
    loadData()
  }

  /**
   * Check if there is any data.
   */
  private fun checkForAvailableData() {
    // Only allow to open graph if there is data saved
    if (AppCollections.entryList.size > 2) {
      openGraphWindow()
    } else {
      Snackbar.make(
        findViewById(R.id.mainLayout),
        R.string.not_enough_data, Snackbar.LENGTH_SHORT
      )
        .show()
    }
  }

  /**
   * Log available sensors. Use for development purposes.
   */
  private fun logAvailableSensors() {
    val deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
    deviceSensors.forEach { sensor -> Log.i("Available sensors", sensor.toString()) }
  }

  /**
   * Start listening to sensors.
   */
  private fun startSensorListeners() {
    // get pressure sensor and use listener to add to textview
    pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    pressureListener =
       SimpleSensorListener(pressureView, "hpa", "Pressure")

    // get light sensor and use listener to add to textview
    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    lightListener =
       SimpleSensorListener(lightView, "lx", "Light")

    temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    // Test if ambient temperature sensor is available because only a few devices have it
    temperatureListener = SimpleSensorListener(
      temperatureView,
      "°C",
      "Temperature"
    )
  }

  /**
   * Resume listening to sensors after pause.
   */
  override fun onResume() {
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

  /**
   * Unregister the sensors when the activity pauses to save device performance and battery.
   */
  override fun onPause() {
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

  private fun loadData() {
    try {
      val fileInputStream = openFileInput(Constants.STORAGE_FILE)
      AppCollections.entryList = jsonFileHandler.getData(fileInputStream)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  /**
   * Save data to data.json.
   */
  private fun saveData() {
    val outputStream: FileOutputStream = openFileOutput(Constants.STORAGE_FILE, Context.MODE_PRIVATE)
    val pressure = pressureListener.value
    val light = lightListener.value

    AppCollections.entryList.add(
      EnvironmentDataEntry(
        EnvironmentDataEntry.LOCATION_UNKNOWN,
        pressure!!,
        light!!,
        temperatureListener?.value ?: 0f // Temperature listener is not available on  most devices.
      )
    )
    jsonFileHandler.putData(outputStream, AppCollections.entryList)
  }
}
