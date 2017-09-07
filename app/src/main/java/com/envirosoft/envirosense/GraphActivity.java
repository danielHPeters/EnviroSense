package com.envirosoft.envirosense;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.envirosoft.envirosense.services.JsonFileReader;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

    private GraphView graph;

    private List<EnvironmentDataEntry> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Start loading data for Graph
        try {

            LineGraphSeries<DataPoint> series;

            String fileName = "data.json";

            FileInputStream fileInputStream = openFileInput(fileName);

            list = JsonFileReader.getEntriesFromJson(fileInputStream);


            // Create points
            DataPoint[] points = new DataPoint[list.size()];

            for (int i = 0; i < list.size(); i++) {
                points[i] = new DataPoint(list.get(i).getEntryDate().getTime(), Float.valueOf(list.get(i).getEntryPressure()) * 100);
            }


            // Get the graph and add the points
            graph = (GraphView) findViewById(R.id.dataGraph);
            series = new LineGraphSeries<>(points);
            graph.addSeries(series);


            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this, dateFormat));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
            graph.getViewport().setMinX(Collections.min(list).getEntryDate().getTime());
            graph.getViewport().setMaxX(Collections.max(list).getEntryDate().getTime());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}
