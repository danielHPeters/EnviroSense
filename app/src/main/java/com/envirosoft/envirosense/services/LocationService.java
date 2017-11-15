package com.envirosoft.envirosense.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationService implements LocationListener {
  private Context context;
  private TextView locationView;
  private Location location;
  private LocationManager locationManager;
  private boolean gpsEnabled;
  private boolean networkEnabled;
  private boolean locationEnabled;
  private double latitude;
  private double longitude;
  private static final long MIN_UPDATE_DISTANCE = 10;
  private static final long MIN_UPDATE_INTERVAL = 60000;

  /**
   * Default constructor. Requires a context as param.
   *
   * @param context      the calling context
   * @param locationView view to display the location on
   */
  public LocationService(Context context, TextView locationView) {
    this.context = context;
    this.locationView = locationView;
    this.locationManager =
        (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
    this.gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    this.networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    initLocation();
  }

  @Override
  public void onLocationChanged(Location location) {
    System.out.println(location.toString());
    this.locationView.setText(location.toString());
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

  private void initLocation() {
    try {
      if (gpsEnabled && networkEnabled) {
        locationEnabled = true;
        // First get location from Network Provider
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            MIN_UPDATE_INTERVAL,
            MIN_UPDATE_DISTANCE, this);
        Log.d("Network", "Network");
        if (locationManager != null) {
          location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
          if (location != null) {
            locationView.setText(location.toString());
            latitude = location.getLatitude();
            longitude = location.getLongitude();
          }
        }
        // if GPS Enabled get lat/long using GPS Services
        if (gpsEnabled) {
          if (location == null) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_UPDATE_INTERVAL,
                MIN_UPDATE_DISTANCE, this);
            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
              location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              if (location != null) {
                locationView.setText(location.toString());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
              }
            }
          }
        }
      }

    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }

  public Location getLocation() {
    return location;
  }
}
