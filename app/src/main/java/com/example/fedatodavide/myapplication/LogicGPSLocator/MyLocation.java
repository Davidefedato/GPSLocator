package com.example.fedatodavide.myapplication.LogicGPSLocator;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import java.util.TimerTask;

public class MyLocation {

    //DICHIARAZIONE ED EVENTUALE INIZIALIZZAZIONE DELLE VARIABILI
    private LocationManager lm;
    private LocationResult locationResult;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private Context context;
    private int aggiornamentiPerSecondo = 1;

    //METODO PER RILEVARE LA POSIZIONE DAL GPS OPPURE DAL PROVIDER INTERNET
    public boolean startListening(Context context, LocationResult result) {
        this.context = context;
        locationResult = result;

        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled)
            return false;

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 / aggiornamentiPerSecondo, 0, locationListenerGps);

        if (network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 / aggiornamentiPerSecondo, 0, locationListenerNetwork);

        return true;
    }

    //LISTENER CHE SI METTE IN ASCOLTO PER RICEVERE I DATI DAL GPS
    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationResult.gotLocation("GPS", location);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    //LISTENER CHE SI METTE IN ASCOLTO PER RICEVERE I DATI DAL PROVIDER INTERNET
    LocationListener locationListenerNetwork = new LocationListener() {
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
    };

    //SOTTOCLASSE CHE CONTIENE LA POSIZIONE CHE VERRA' USATA PER TRACCIARE IL PERCORSO EFFETTUATO
    public static abstract class LocationResult {
        public abstract void gotLocation(String type, Location location);
    }

    //METODO CHE FERMA LA LOCALIZZAZIONE
    public void stopListening() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.removeUpdates(locationListenerNetwork);
        lm.removeUpdates(locationListenerGps);
    }
}
