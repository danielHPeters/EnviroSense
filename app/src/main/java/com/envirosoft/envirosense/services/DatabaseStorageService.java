package com.envirosoft.envirosense.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.net.ConnectivityManagerCompat;

/**
 * Created by daniel on 08.09.17.
 */

public class DatabaseStorageService {

    public DatabaseStorageService(Context context) {

        if(isConnected(context)){

        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() ? true : false;
    }
}
