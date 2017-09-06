package com.envirosoft.envirosense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button btn = (Button) findViewById(R.id.loadButton);

        final TextView temp = (TextView) findViewById(R.id.textView);

        /*try {
            JSONArray jsonArray = new JSONArray(loadData());

            jsonArray.
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setText(loadData());
            }
        });




        /*GraphView graph = (GraphView) findViewById(R.id.dataGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/
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
