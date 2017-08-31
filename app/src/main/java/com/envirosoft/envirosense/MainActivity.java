package com.envirosoft.envirosense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * Instantiate an Intent to open the graph activity
     */
    public void openGraphWindow(){
        Intent openGraph = new Intent(this, GraphActivity.class);
        startActivity(openGraph);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
