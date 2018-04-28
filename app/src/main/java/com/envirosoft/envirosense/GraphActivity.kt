package com.envirosoft.envirosense

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.envirosoft.envirosense.data.AppCollections
import com.envirosoft.envirosense.util.EnvironmentDataLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*

class GraphActivity: AppCompatActivity() {
  private lateinit var graph: GraphView
  private lateinit var calendar: Calendar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_graph)
    graph = findViewById(R.id.dataGraph)
    calendar = Calendar.getInstance()
    initializeGraph()
  }

  private fun initializeGraph() {
    val series: LineGraphSeries<DataPoint>
    val list = AppCollections.entryList
    val points = ArrayList<DataPoint>()

    list.forEach {entry ->  points.add(DataPoint(entry.date, (entry.pressure * 100).toDouble())) }
    // add the points to the graph
    series = LineGraphSeries( points.toTypedArray())
    graph.addSeries(series)
    graph.title = "Air Pressure Graph"

    // Set Labels for graph
    graph.gridLabelRenderer.numHorizontalLabels = 3
    graph.gridLabelRenderer.labelFormatter = EnvironmentDataLabelFormatter()
  }
}
