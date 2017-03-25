package com.example.fedatodavide.myapplication.Logic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.TimerTask;

/**
 * Created by fedatodavide on 24/03/2017.
 */
public class MyLocation {

    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context context;

    private int aggiornamentiPerSecondo = 1;

    public boolean startListening(Context context, LocationResult result) {

        this.context = context;
        locationResult = result;

        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        /*try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }*/

        if (!gps_enabled /*&& !network_enabled*/)
            return false;

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000/aggiornamentiPerSecondo, 0, locationListenerGps);

        /*if (network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000/aggiornamentiPerSecondo, 0, locationListenerNetwork);*/

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationResult.gotLocation("GPS", location);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //lm.removeUpdates(this);
            //lm.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    /*LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationResult.gotLocation("INTERNET", location);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };*/

    class GetLastLocation extends TimerTask {
        @Override

        public void run() {
            Location net_loc = null, gps_loc = null;
            if (gps_enabled)
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(network_enabled)
                net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            if(gps_loc!=null && net_loc!=null){
                if(gps_loc.getTime()>net_loc.getTime())
                    locationResult.gotLocation("GPS", gps_loc);
                else
                    locationResult.gotLocation("INTERNET", net_loc);
                return;
            }

            if(gps_loc!=null){
                locationResult.gotLocation("GPS", gps_loc);
                return;
            }
            if(net_loc!=null){
                locationResult.gotLocation("INTERNET", net_loc);
                return;
            }
            locationResult.gotLocation("", null);
        }
    }

    public static abstract class LocationResult{
        public abstract void gotLocation(String type, Location location);
    }
}
