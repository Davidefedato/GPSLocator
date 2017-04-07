package com.example.fedatodavide.myapplication;

/**
 * Created by fedatodavide on 25/03/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedatodavide.myapplication.Logic.CanvasView;
import com.example.fedatodavide.myapplication.Logic.DrawTest;
import com.example.fedatodavide.myapplication.Logic.MyLocation;

/**
 * Created by fedatodavide on 24/03/2017.
 */
public class LocationFinder extends Activity {

    private MyLocation myLocation = new MyLocation();
    private TextView text;
    //private DrawTest drawTest;
    private CanvasView customCanvas;
    float PercentualeX = 0;
    float PercentualeY = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_finder);
        text = (TextView)findViewById(R.id.position);
        text.setText("Ciao Davide!!!");




        boolean r = myLocation.startListening(getApplicationContext(), locationResult);

        //TEMP Thread di prova t.start();
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        //customCanvas.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.casa));//cambia sfondo canvas
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(final String type, Location location) {
            final float Longitude = ((float) location.getLatitude ());
            final float Latitude = ((float) location.getLongitude ());

            System.out.println(type + ": Long: " + Longitude + " Lat: " + Latitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int larghezza = customCanvas.larghezza;
                    int altezza = customCanvas.altezza;

                    String debug = "";

                    debug += "Canvas: "+ larghezza + ", " + altezza + "\n";

                    System.out.println("larghezza " + larghezza);   //540
                    System.out.println("altezza " + altezza);   //886

                    //Scuola
                    //Start: 45.770759 12.046336
                    //End: 45.777010 12.046532

                    /*45.047199 12.144856 alto sx
                    45.041041 12.150161 basso dx*/

                    //45.044396 12.146119

                    /*LONGITUDINE
                      45.047199 : 0 = 45.041041 : 886
                      lat : x  =  45.041041 : 886

                      LATITUDINE
                      long : x = 12.150161 : 540
                      */

                    /*
                    posx = ((Longitude * (float)45.041041) / 100);
                    posy = ((Latitude * (float)12.150161) / 100);
                    */

                    debug += "Posizione: " + Longitude + ", " + Latitude + "\n";
                    debug += "Min map: " + 45.770759 + ", " + 12.046336 + "\n";
                    debug += "Max map: " + 45.777010 + ", " + 12.046532 + "\n";

                    PercentualeX = (float)(Longitude - 45.770759 * 100 / (45.777010 - 45.770759));
                    PercentualeY = (float)((Latitude - 12.046336) * 100 / (12.046532 - 12.046336));

                    debug += "Percentuale: " + PercentualeX + ", " + PercentualeY + "\n";

                    float toCanvasX = (PercentualeX * larghezza) / 100;
                    float toCanvasY = (PercentualeY * altezza) / 100;

                    debug += "CanvasPercent: " + toCanvasX + ", " + toCanvasY;

                    Toast.makeText(getApplicationContext(), "Posx : " + toCanvasX + "% Posy : " + toCanvasY + "%", Toast.LENGTH_SHORT).show();
                    text.setText(debug);
                    //QUI dai la posizione al canvas ->
                    customCanvas.drawPoint(toCanvasX, toCanvasY);
                }
            });
        }
    };


    //TEMP (serve per fare il test di canvas.draw())
    /*private Thread t = new Thread(new Runnable() {
        int c = 0;
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(10);
                    c += 1;
                    drawOnCanvas(c, c);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    });

    //TEMP^
    private void drawOnCanvas(final float posx, final float posy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customCanvas.drawPoint(posx, posy);
            }
        });
    }*/
    //FINE
}
