package com.envirosoft.envirosense.service

import android.util.JsonWriter

import com.envirosoft.envirosense.model.EnvironmentDataEntry

import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

object JsonFileSaver {
  /**
   * Write data to a JSON file stream.
   *
   * @param out  output stream to write to
   * @param data data to be written to JSON
   */
  private fun writeJsonStream(out: OutputStream,  data: MutableCollection<EnvironmentDataEntry>) {
    val writer =  JsonWriter( OutputStreamWriter(out, "UTF-8"))
    writer.setIndent("  ")
    writeListToJsonArray(writer, data)
    writer.close()
  }

  /**
   *
   */
  private fun writeListToJsonArray(writer: JsonWriter,  entries: MutableCollection<EnvironmentDataEntry>) {
    writer.beginArray()
    entries.forEach { entry -> writeEntry(writer, entry)}
    writer.endArray()
  }

  /**
   * Write object attributes.
   *
   * @param writer json writer
   * @param entry  data entry
   */
  private fun writeEntry( writer: JsonWriter,  entry: EnvironmentDataEntry) {
    writer.beginObject()
    writer.name("date").value(entry.date.toString())
    writer.name("location").value(entry.location)
    writer.name("pressure").value(entry.pressure)
    writer.name("luminance").value(entry.luminance)
    writer.name("temperature").value(entry.temperature)
    writer.endObject()
  }

  /**
   * Save to stream.
   *
   * @param outputStream output stream
   */
  fun saveToFileStream(outputStream: FileOutputStream,  list: MutableCollection<EnvironmentDataEntry>) {
    JsonFileSaver.writeJsonStream(outputStream, list)
    outputStream.close()
  }
}
