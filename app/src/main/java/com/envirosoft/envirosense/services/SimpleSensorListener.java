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

    private String measurement;

    private String value;

    private String sensorType;

    public SimpleSensorListener(TextView view, String measurement, String sensorType) {
        this.view = view;
        this.measurement = measurement;
        this.sensorType = sensorType;
        this.value = "";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float val = event.values[0];
        this.value = String.valueOf(val);
        this.view.setText(this.sensorType + ": " + this.value + " " + this.measurement);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public String getValue() {
        return value;
    }
}
