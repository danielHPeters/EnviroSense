package com.envirosoft.envirosense;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GraphActivity extends AppCompatActivity {

    private DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button btn = (Button) findViewById(R.id.loadButton);

        final TextView temp = (TextView) findViewById(R.id.textView);

        List<EnvironmentDataEntry> list = new ArrayList<>();

        String date;

        String pressure;

        String light;

        String temperature;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setText(loadData());
            }
        });

        try {
            JSONArray jsonArray = new JSONArray(loadData());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                date = jsonObject.get("entryDate").toString();
                pressure = jsonObject.get("entryPressure").toString();
                light = jsonObject.get("entryLight").toString();
                temperature = jsonObject.get("entryTemperature").toString();


                Date parsedDate = this.df.parse(date);

                list.add(new EnvironmentDataEntry(parsedDate, pressure, light, temperature));

                System.out.println(date + pressure + light + temperature);


            }

            DataPoint[] points = new DataPoint[list.size()];

            for (int i = 0; i < list.size(); i++) {
                points[i] = new DataPoint(list.get(i).getEntryDate().getTime(), Float.valueOf(list.get(i).getEntryPressure()) * 100);
            }

            //Collections.sort(list, Collections.<EnvironmentDataEntry>reverseOrder());

            /*for(EnvironmentDataEntry e : list){
                System.out.println(e.getEntryDate().getTime());
            }*/

            /*DataPoint[] points = new DataPoint[]{
                    new DataPoint(1, 2),
                    new DataPoint(2, 3),
                    new DataPoint(3,4)
            };*/


            //System.out.println(points.length);
            GraphView graph = (GraphView) findViewById(R.id.dataGraph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
            graph.getViewport().setMinX(list.get(0).getEntryDate().getTime());
            graph.getViewport().setMaxX(list.get(list.size()).getEntryDate().getTime());
            graph.getViewport().setXAxisBoundsManual(true);

            graph.addSeries(series);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public String loadData() {

        String filename = "data.json";
        FileInputStream inputStream;

        StringBuilder sb = new StringBuilder();

        try {
            inputStream = openFileInput(filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
