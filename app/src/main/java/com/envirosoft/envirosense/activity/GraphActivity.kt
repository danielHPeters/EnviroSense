package com.envirosoft.envirosense.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.envirosoft.envirosense.R

import com.envirosoft.envirosense.data.AppCollections
import com.envirosoft.envirosense.util.AirPressureLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*

/**
 * Activity which displays a graph with the saved data.
 *
 * @author Jason Millsom, Daniel Peters
 * @version 1.2
 */
class GraphActivity : AppCompatActivity() {
  private lateinit var graph: GraphView

  /**
   * Initialize layout.
   *
   * @param savedInstanceState Instance state
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_graph)
    graph = findViewById(R.id.dataGraph)
    initializeGraph()
  }

  /**
   * Initialize the graph view with data.
   */
  private fun initializeGraph() {
    val series: LineGraphSeries<DataPoint>
    val list = AppCollections.entryList
    val points = ArrayList<DataPoint>()

    list.forEach { entry -> points.add(DataPoint(entry.date, (entry.pressure * 100).toDouble())) }
    // add the points to the graph
    series = LineGraphSeries(points.toTypedArray())
    graph.addSeries(series)
    graph.title = "Air Pressure Graph"

    // Set Labels for graph
    graph.gridLabelRenderer.numHorizontalLabels = 3
    graph.gridLabelRenderer.labelFormatter = AirPressureLabelFormatter()
  }
}
