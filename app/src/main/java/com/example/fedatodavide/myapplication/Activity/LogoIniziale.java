package com.example.fedatodavide.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.fedatodavide.myapplication.R;

import static com.example.fedatodavide.myapplication.R.drawable.si;

public class LogoIniziale extends Activity {

    //DICHIARAZIONE ED EVENTUALE INIZIALIZZAZIONE DELLE VARIABILI
    ImageView i;
    private int attesa = 2000;

    //METODO CHE MOSTRA PER TRE SECONDI IL LOGO DELL'APPLICAZIONE E POI LANCIA L'ACTIVITY DI LOGIN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_iniziale);

        //PRENDO IL RIFERIMENTO DELL'OGGETTO GRAFICO GIA' PRECEDENTEMENTE CREATO
        i = (ImageView) findViewById(R.id.imageView3);

        //IMPOSTO LO SFONDO ALL'IMAGEVIEW
        i.setImageResource(si);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        }, attesa);
    }
}

