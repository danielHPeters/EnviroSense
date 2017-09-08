package com.envirosoft.envirosense.tasks;

import android.os.AsyncTask;

/**
 * Created by daniel on 08.09.17.
 */

public class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        System.out.println("Data Sent");
    }
}
