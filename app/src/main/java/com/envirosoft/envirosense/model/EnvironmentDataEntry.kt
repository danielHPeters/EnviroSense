package com.envirosoft.envirosense.model

import java.io.Serializable
import java.util.Date
import java.util.UUID

data class EnvironmentDataEntry(
  val location: String,
  val pressure: Float,
  val luminance: Float,
  val temperature: Float,
  val date: Date = Date(),
  val id: UUID = UUID.randomUUID()
) : Comparable<EnvironmentDataEntry>, Serializable {
  companion object {
    const val LOCATION_UNKNOWN = "unknown"
  }

  override fun compareTo(other: EnvironmentDataEntry): Int {
    return date.time.compareTo(other.date.time)
  }
}
