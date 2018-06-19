package com.envirosoft.envirosense.task

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Update time on a TextView in loop.
 */
class TimeUpdater(looper: Looper, view: TextView) {
  val handler = Handler(looper)
  val runnable = object : Runnable {
    override fun run() {
      view.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
      handler.postDelayed(this, 1000)
    }
  }
}