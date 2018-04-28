package com.envirosoft.envirosense.service

import com.envirosoft.envirosense.model.EnvironmentDataEntry
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object JsonFileReader {
  private val dateFormat: DateFormat = SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH)

  /**
   * Load the json file to string.
   *
   * @return loaded dataL
   */
  fun loadData(stream: FileInputStream): String {
    val sb = StringBuilder()

    try {
      val reader = BufferedReader(InputStreamReader(stream))
      reader.readLine().forEach { line -> sb.append(line).append("\n") }
      reader.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return sb.toString()
  }

  /**
   * Load the JSON file into an ArrayList of EnvironmentDataEntry objects.
   *
   * @return data as List
   */
  fun getEntriesFromJson(fileInputStream: FileInputStream): MutableList<EnvironmentDataEntry> {
    val entriesList = ArrayList<EnvironmentDataEntry>()
    val jsonArray = JSONArray(JsonFileReader.loadData(fileInputStream))

    for (i in 0..jsonArray.length()) {
      val jsonObject = JSONObject(jsonArray.get(i).toString())
      val date = jsonObject.get("date").toString()
      val location = jsonObject.get("location").toString()
      val pressure = jsonObject.get("pressure") as Float
      val light = jsonObject.get("luminance") as Float
      val temperature = jsonObject.get("temperature") as Float
      val parsedDate = dateFormat.parse(date)
      entriesList.add(EnvironmentDataEntry(location, pressure, light, temperature, parsedDate))
    }
    return entriesList
  }
}
