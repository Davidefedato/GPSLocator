package com.example.fedatodavide.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fedatodavide.myapplication.R;

public class LogoIniziale extends Activity {
    //METODO CHE MOSTRA PER TRE SECONDI IL LOGO DELL'APPLICAZIONE E POI LANCIA L'ACTIVITY DI LOGIN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_iniziale);

       /* try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Intent i = new Intent(getApplicationContext(), Login.class);
        //startActivity(i);
        //finish();
    }
}
