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

    @Override
    public void onSensorChanged(SensorEvent event) {
        float value = event.values[0];
        this.view.setText(String.valueOf(value));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
