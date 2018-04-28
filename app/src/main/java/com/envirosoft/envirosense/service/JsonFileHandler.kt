package com.envirosoft.envirosense.service

import com.envirosoft.envirosense.interfaces.IDataHandler
import com.envirosoft.envirosense.model.EnvironmentDataEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

class JsonFileHandler: IDataHandler<EnvironmentDataEntry> {
  private val gson = Gson()

  override fun putData(outputStream: OutputStream, data: MutableList<EnvironmentDataEntry>) {
    gson.toJson(data,  OutputStreamWriter(outputStream, "UTF-8"))
  }

  override fun getData(inputStream: InputStream): MutableList<EnvironmentDataEntry> {
    val listType =  object : TypeToken<List<EnvironmentDataEntry>>() {}.type
    return gson.fromJson<MutableList<EnvironmentDataEntry>>(BufferedReader(InputStreamReader(inputStream)), listType)
  }
}