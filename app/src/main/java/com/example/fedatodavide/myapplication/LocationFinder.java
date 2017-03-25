package com.example.fedatodavide.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by fedatodavide on 24/03/2017.
 */
public class LocationFinder extends Activity {

    MyLocation myLocation = new MyLocation();
    private TextView text;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.position);
        text.setText("Ciao Davide!!!");
        boolean r = myLocation.startListening(getApplicationContext(), locationResult);
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(final String type, Location location) {
            final double Longitude = location.getLongitude();
            final double Latitude = location.getLatitude();

            System.out.println(type + ": Long: " + Longitude + " Lat: " + Latitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(type + ": Long: " + Longitude + " Lat: " + Latitude);
                }
            });
        }
    };
}
