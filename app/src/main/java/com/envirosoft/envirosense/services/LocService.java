package com.envirosoft.envirosense.services;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class LocService implements LocationListener {
  private LocationManager locationManager;
  private Context context;
  private Criteria criteria;
  private String provider;
  private TextView view;

  /**
   * Default constructor.
   *
   * @param context context
   * @param view    calling view
   */
  public LocService(Context context, TextView view) {
    this.context = context;
    this.view = view;
    initializeLocationService();
  }

  private void initializeLocationService() {

  }

  @Override
  public void onLocationChanged(Location location) {
    String msg = "New Latitude: "
        + location.getLatitude()
        + "New Longitude: "
        + location.getLongitude();
    this.view.setText(msg);
  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {
    Toast.makeText(this.context, "Gps is turned on! ", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onProviderDisabled(String s) {
    Toast.makeText(this.context, "Gps is turned off!! ", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    context.startActivity(intent);
  }
}
