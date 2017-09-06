package com.envirosoft.envirosense.services;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by admin on 31.08.2017.
 */
public class SimpleSensorListener implements SensorEventListener {

    private TextView view;

    private String measurementType;

    private String value;

    private String sensorType;

    public SimpleSensorListener (TextView view, String measurementType, String sensorType){
        this.view = view;
        this.measurementType = measurementType;
        this.sensorType = sensorType;
        this.value = "";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float val = event.values[0];
        this.value = String.valueOf(val);
        this.view.setText(this.sensorType + ": " + this.value + " " + this.measurementType);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public String getValue() {
        return value;
    }
}
