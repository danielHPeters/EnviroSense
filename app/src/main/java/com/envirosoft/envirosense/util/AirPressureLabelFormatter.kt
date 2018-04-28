package com.envirosoft.envirosense.util

import com.envirosoft.envirosense.config.Constants
import com.jjoe64.graphview.DefaultLabelFormatter
import java.util.Calendar

/**
 * Custom LabelFormatter for the GraphView.
 *
 * @author Daniel Peters
 * @version 1.0
 */
class AirPressureLabelFormatter : DefaultLabelFormatter() {
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