package com.example.fedatodavide.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fedatodavide.myapplication.LogicDatabase.User;
import com.example.fedatodavide.myapplication.LogicGPSLocator.CanvasView;
import com.example.fedatodavide.myapplication.LogicGPSLocator.MyLocation;
import com.example.fedatodavide.myapplication.R;

public class LocationFinder extends Activity {

    //DICHIARAZIONE DELLE VARIABILI
    private MyLocation myLocation = new MyLocation();
    private CanvasView customCanvas;
    float PercentualeX = 0;
    float PercentualeY = 0;
    private int millisecondi = 0;
    private int secondi = 0;
    private int minuti = 0;
    Button Inizio;
    Button FineGiro;
    Button Fine;
    Thread t;
    String tempo = "";
    private int iniziato;
    private int giro;



    //DATI RIGUARDANTI LA MAPPA DELLA SCUOLA
    private double mapXMin = 12.045191;
    private double mapXMax = 12.047382;
    private double mapYMin = 45.771859;
    private double mapYMax = 45.77048;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_finder);
        Inizio = (Button) findViewById(R.id.btnInizio);
        FineGiro = (Button) findViewById(R.id.btnFineGiro);
        Fine = (Button) findViewById(R.id.btnFine);
        iniziato = 0;
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        //BOTTONE CHE AVVIA IL CRONOMETRO E LA LOCALIZZAZIONE
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

        //BOTTONE CHE SALVA IL TEMPO USATO PER COMPIERE UN GIRO E AZZERA IL CRONOMETRO PER RACCOGLIERE IL PROSSIMO TEMPO
        FineGiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iniziato == 1){
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
            }
        });

        //BLOCCA IL CRONOMETRO DEFINITIVAMENTE E FA PARTIRE L'ACTIVITY PER LA GESTIONE DEI DATI
        Fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iniziato == 1 && giro!= 0){
                    myLocation.stopListening();
                    t.interrupt();
                    Intent intent = new Intent(getApplicationContext(), ResoContoDati.class);
                    startActivity(intent);
                }
                iniziato = 0;
            }
        });

    }

    //FUNZIONE CHE CALCOLA LA POSIZIONE NEL CANVAS E DISEGNA IL PUNTINO SULLA MAPPA
    private void calcolaPosizione(final double x, final double y) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int larghezza = customCanvas.larghezza;
                int altezza = customCanvas.altezza;

                double deltaMapX = Math.abs(mapXMax - mapXMin);
                double deltaMapY = Math.abs(mapYMax - mapYMin);

                PercentualeX = (float) Math.abs((x - mapXMin) * 100 / deltaMapX);
                PercentualeY = (float) Math.abs((y - mapYMin) * 100 / deltaMapY);


                float toCanvasX = (PercentualeX * larghezza) / 100;
                float toCanvasY = (PercentualeY * altezza) / 100;

                //QUI dai la posizione al canvas ->
                customCanvas.drawPoint(toCanvasX, toCanvasY);
            }
        });
    }

    //AVVIO DELLA FUNZIONE CHE RECUPERA LA POSIZIONE
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

    //FUNZIONE CHE LANCIA SEMPRE L'ACTIVITY DI LOGIN QUANDO PREMO IL TASTO INDIETRO
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }
}
