package com.envirosoft.envirosense.services;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by daniel on 08.09.17.
 */

public class LocationService implements LocationListener {

    private LocationManager locationManager;

    private Criteria criteria;

    private String provider;

    public LocationService(Context context) {
        initializeLocationService(context);
    }

    private void initializeLocationService(Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
