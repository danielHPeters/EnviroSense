package com.envirosoft.envirosense;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.envirosoft.envirosense.services.JsonFileSaver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<EnvironmentDataEntry> list;

    /**
     * Instantiate an Intent to open the graph activity
     */
    public void openGraphWindow() {
        Intent openGraph = new Intent(this, GraphActivity.class);
        startActivity(openGraph);
    }

    public void saveData() {


        String filename = "data.json";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            JsonFileSaver.writeJsonStream(outputStream, list);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadData() {

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

            TextView helloText = (TextView) findViewById(R.id.helloText);

            helloText.setText(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.list = new ArrayList<>();
        list.add(new EnvironmentDataEntry("20", "18"));
        list.add(new EnvironmentDataEntry("15", "4"));
        list.add(new EnvironmentDataEntry("46", "32"));

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        Button graphBtn = (Button) findViewById(R.id.graphBtn);

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
}
