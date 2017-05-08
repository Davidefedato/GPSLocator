package com.example.fedatodavide.myapplication;

/**
 * Created by fedatodavide on 25/03/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedatodavide.myapplication.LogicDatabase.User;
import com.example.fedatodavide.myapplication.LogicGPSLocator.CanvasView;
import com.example.fedatodavide.myapplication.LogicGPSLocator.MyLocation;

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
    private int millisecondi = 0;
    private int secondi = 0;
    private int minuti = 0;
    Button Inizio;
    Button FineGiro;
    Button Fine;
    Chronometer cronometro;
    Thread t;
    String tempo = "";
    private int iniziato;
    private int giro;


    //Dati sulla mappa
    private double mapXMin = 12.045191;
    private double mapXMax = 12.047382;
    private double mapYMin = 45.771859;
    private double mapYMax = 45.77048;
    //prova

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_finder);
        Inizio = (Button) findViewById(R.id.btnInizio);
        FineGiro = (Button) findViewById(R.id.btnFineGiro);
        Fine = (Button) findViewById(R.id.btnFine);
        iniziato = 0;
        //TEMP Thread di prova t.start();
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        Inizio.setOnClickListener(new View.OnClickListener() {
            int secondi = 0;

            @Override
            public void onClick(View v) {

                millisecondi = 0;
                minuti = 0;
                secondi = 0;
                iniziato = 1;
                final boolean r = myLocation.startListening(getApplicationContext(), locationResult);
                t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            millisecondi++;
                        }
                    }
                });
                t.start();
            }
        });


        FineGiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giro++;

                while (millisecondi > 1000) {
                    secondi++;
                    millisecondi = millisecondi - 1000;

                    while (secondi > 60) {
                        minuti++;
                        secondi = secondi - 60;
                    }
                }


                tempo = minuti + " : " + secondi + " : " + millisecondi;
                User.tempi.add(tempo);

                Log.i("Tempo", tempo);

                millisecondi = 0;
                minuti = 0;
                secondi = 0;


            }
        });

        Fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iniziato == 1 && giro!= 0){
                    myLocation.stopListening();
                    t.interrupt();
                    Intent intent = new Intent(getApplicationContext(), ResoContoDati.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void calcolaPosizione(final double x, final double y) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int larghezza = customCanvas.larghezza;
                int altezza = customCanvas.altezza;

                String debug = "";

                debug += "Canvas: " + larghezza + ", " + altezza + "\n";

                System.out.println("larghezza " + larghezza);   //540
                System.out.println("altezza " + altezza);   //886


                //debug += "Posizione: " + x + ", " + y + "\n";
                //debug += "Min map: " + 45.770759 + ", " + 12.046336 + "\n";
                //debug += "Max map: " + 45.777010 + ", " + 12.046532 + "\n";
                double deltaMapX = Math.abs(mapXMax - mapXMin);
                double deltaMapY = Math.abs(mapYMax - mapYMin);

                //debug += "DeltaMap: " + deltaMapX + ", " + deltaMapY + "\n";
                PercentualeX = (float) Math.abs((x - mapXMin) * 100 / deltaMapX);
                PercentualeY = (float) Math.abs((y - mapYMin) * 100 / deltaMapY);

                //debug += "Percentuale: " + PercentualeX + ", " + PercentualeY + "\n";

                float toCanvasX = (PercentualeX * larghezza) / 100;
                float toCanvasY = (PercentualeY * altezza) / 100;

                //debug += "CanvasPercent: " + toCanvasX + ", " + toCanvasY;

                //Toast.makeText(getApplicationContext(), "Posx : " + toCanvasX + "% Posy : " + toCanvasY + "%", Toast.LENGTH_SHORT).show();
//                text.setText(debug);
                //QUI dai la posizione al canvas ->
                customCanvas.drawPoint(toCanvasX, toCanvasY);
            }
        });
    }

    public MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {

        @Override
        public void gotLocation(final String type, Location location) {
            final float Longitude = ((float) location.getLongitude());
            final float Latitude = ((float) location.getLatitude());
            System.out.println(type + ": Long: " + Longitude + " Lat: " + Latitude);
            calcolaPosizione(Longitude, Latitude);
            return;
        }
    };

    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }
}
