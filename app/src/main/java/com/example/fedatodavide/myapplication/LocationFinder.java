package com.example.fedatodavide.myapplication;

/**
 * Created by fedatodavide on 25/03/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fedatodavide.myapplication.Logic.DrawTest;
import com.example.fedatodavide.myapplication.Logic.MyLocation;

/**
 * Created by fedatodavide on 24/03/2017.
 */
public class LocationFinder extends Activity {

    private MyLocation myLocation = new MyLocation();
    private TextView text;
    private DrawTest drawTest;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_finder);
        text = (TextView)findViewById(R.id.position);
        text.setText("Ciao Davide!!!");

        boolean r = myLocation.startListening(getApplicationContext(), locationResult);

        drawTest = new DrawTest(this);
        drawTest.setBackgroundColor(Color.WHITE);
        setContentView(drawTest);
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
