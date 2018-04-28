package com.envirosoft.envirosense.util

import com.envirosoft.envirosense.config.Constants
import com.jjoe64.graphview.DefaultLabelFormatter
import java.util.*

class EnvironmentDataLabelFormatter: DefaultLabelFormatter() {
  private val calendar = Calendar.getInstance()
  override fun formatLabel(value: Double, isValueX: Boolean): String {
    return if (isValueX) {
      calendar.timeInMillis = value.toLong()
      Constants.DATE_FORMAT.format(calendar.timeInMillis)
    } else {
      // show pressure unit for y value
      super.formatLabel(value, false) + " hpa"
    }
  }
}