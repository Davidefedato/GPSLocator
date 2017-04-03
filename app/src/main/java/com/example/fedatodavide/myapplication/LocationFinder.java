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
            final double Longitude = location.getLongitude();
            final double Latitude = location.getLatitude();
            System.out.println(type + ": Long: " + Longitude + " Lat: " + Latitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(type + ": Long: " + Longitude + " Lat: " + Latitude);

                    int width = customCanvas.larghezza;
                    int height = customCanvas.altezza;

                    System.out.println("WIDTH " + width);   //540
                    System.out.println("HEIGHT " + height);   //886
                    /*45.047199 12.144856 alto sx
                    45.041041 12.150161 basso dx*/

                    //int H;
                    //int W;

                    //QUI dai la posizione al canvas ->
                    //customCanvas.drawPoint(posx, posy);
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
