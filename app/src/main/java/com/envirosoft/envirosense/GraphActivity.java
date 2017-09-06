package com.envirosoft.envirosense;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.envirosoft.envirosense.model.EnvironmentDataEntry;

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

        try {
            JSONArray jsonArray = new JSONArray(loadData());

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                date = jsonObject.get("entryDate").toString();
                pressure = jsonObject.get("entryPressure").toString();
                light = jsonObject.get("entryLight").toString();
                 temperature = jsonObject.get("entryTemperature").toString();


                Date parsedDate =  this.df.parse(date);

                list.add(new EnvironmentDataEntry(parsedDate, pressure, light, temperature));

                System.out.println(date + pressure + light + temperature);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


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
