package com.envirosoft.envirosense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.envirosoft.envirosense.data.AppCollections;
import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GraphActivity extends AppCompatActivity {
  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
  private GraphView graph;
  private Calendar calendar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_graph);
    graph = (GraphView) findViewById(R.id.dataGraph);
    calendar = Calendar.getInstance();
    initializeGraph();

  }

  private void initializeGraph() {

    LineGraphSeries<DataPoint> series;

    int maxPoints = AppCollections.entryList.size();

    List<EnvironmentDataEntry> list = AppCollections.entryList;

    // Create points
    DataPoint[] points = new DataPoint[maxPoints];

    for (int i = 0; i < maxPoints; i++) {
      points[i] = new DataPoint(
          list.get(i).getDate().getTime(),
          Float.valueOf(list.get(i).getPressure()) * 100);
    }


    // add the points to the graph

    series = new LineGraphSeries<>(points);
    graph.addSeries(series);
    graph.setTitle("Air Pressure Graph");

    // Set Labels for graph
    graph.getGridLabelRenderer().setNumHorizontalLabels(3);

    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
      @Override
      public String formatLabel(double value, boolean isValueX) {
        if (isValueX) {
          calendar.setTimeInMillis((long) value);
          return DATE_FORMAT.format(calendar.getTimeInMillis());
        } else {
          // show pressure unit for y value
          return super.formatLabel(value, false) + " hpa";
        }
      }
    });


  }


}
