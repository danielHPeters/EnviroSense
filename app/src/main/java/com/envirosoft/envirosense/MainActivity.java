package com.envirosoft.envirosense;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ServiceCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.envirosoft.envirosense.model.EnvironmentDataEntry;
import com.envirosoft.envirosense.services.JsonFileSaver;
import com.envirosoft.envirosense.services.SimpleSensorListener;

import java.io.FileOutputStream;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private SensorManager sensorManager;

    private Sensor pressureSensor;

    private Sensor lightSensor;

    private Sensor temperatureSensor;

    private SimpleSensorListener pressureListener;

    private SimpleSensorListener lightListener;

    private SimpleSensorListener temperatureListener;

    private List<EnvironmentDataEntry> list;

    private TextView pressureView;

    private TextView lightView;

    private TextView temperatureView;

    private Button saveBtn;

    private Button graphBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSensorListeners();

        this.list = new ArrayList<>();
        this.saveBtn = (Button) findViewById(R.id.saveBtn);
        this.graphBtn = (Button) findViewById(R.id.graphBtn);

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Only allow to open graph if there is data saved
                if (list.size() > 2) {
                    openGraphWindow();
                } else {
                    Snackbar.make(findViewById(R.id.mainLayout),
                            R.string.not_enough_data, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void startSensorListeners() {
        // Init sensor manager to access sensors
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // get pressure sensor and use listener to add to textview
        this.pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        this.pressureView = (TextView) findViewById(R.id.pressureView);
        this.pressureListener = new SimpleSensorListener(pressureView, "hpa", "Pressure");

        // get light sensor and use listener to add to textview
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.lightView = (TextView) findViewById(R.id.lightView);
        this.lightListener = new SimpleSensorListener(lightView, "lx", "Light");

        this.temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        this.temperatureView = (TextView) findViewById(R.id.temperatureView);

        // Test if ambient temperature sensor is available because only a few devices have it
        if (temperatureSensor != null) {

            this.temperatureListener = new SimpleSensorListener(temperatureView, "°C", "Temperature");
        } else {
            temperatureView.setText("Temperature Sensor not available!");
        }
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();

        sensorManager.registerListener(pressureListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (temperatureListener != null) {
            sensorManager.registerListener(temperatureListener, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(pressureListener);
        sensorManager.unregisterListener(lightListener);

        if (temperatureListener != null) {
            sensorManager.unregisterListener(temperatureListener);
        }
    }


    /**
     * Instantiate an Intent to open the graph activity
     */
    public void openGraphWindow() {
        Intent openGraph = new Intent(this, GraphActivity.class);
        startActivity(openGraph);
    }

    /**
     * Save data to data.json
     */
    public void saveData() {


        String pressure = pressureListener.getValue();

        String light = lightListener.getValue();

        String temperature = "";

        if (temperatureListener != null) {
            temperature = temperatureListener.getValue();
        }

        this.list.add(new EnvironmentDataEntry(pressure, light, temperature));
        String filename = "data.json";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            JsonFileSaver.saveToFileStream(outputStream, this.list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }


}
