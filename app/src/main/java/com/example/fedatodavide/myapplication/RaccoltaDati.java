package com.example.fedatodavide.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fedatodavide.myapplication.LogicDatabase.DatabaseHelper;
import com.example.fedatodavide.myapplication.LogicDatabase.User;

import java.util.ArrayList;

public class RaccoltaDati extends AppCompatActivity {

    //DICHIARAZIONE DELLE VARIABILI
    private Context context;
    private DatabaseHelper gdh;
    SQLiteDatabase db;

    Button bottone;
    String circuito1;
    String tempo;
    String dati;
    String stringa;
    int i = 0;
    int correctMoto = 0;
    int correctCircuito = 0;
    String queryCat;
    String queryNaz;


    private Spinner categoria;
    private Spinner moto;
    private Spinner nazione;
    private Spinner circuito;

    String queryCategoria;
    String categoriaSelezionata;

    ArrayList<String> categorie;
    ArrayList<String> elencoMoto;

    ArrayList<String> nazioni;
    ArrayList<String> elencoCircuiti;

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> spinnerAdapter1;

    private ArrayAdapter<String> spinnerAdapter2;
    private ArrayAdapter<String> spinnerAdapter3;

    private String s;

    private String motoQuery;
    private String circuitoQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //INIZIALIZZAZIONE DELLE VARIABILI
        context = getBaseContext();
        gdh = new DatabaseHelper(getBaseContext());
        db = gdh.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raccolta_dati);
        bottone = (Button) findViewById(R.id.salvaDati);


        categoria = (Spinner) findViewById(R.id.categoria);
        moto = (Spinner) findViewById(R.id.moto);

        nazione = (Spinner) findViewById(R.id.nazione);
        circuito = (Spinner) findViewById(R.id.circuito);

        categorie = new ArrayList<String>();
        nazioni = new ArrayList<String>();

        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.row);
        spinnerAdapter1 = new ArrayAdapter<String>(this, R.layout.row);

        spinnerAdapter2 = new ArrayAdapter<String>(this, R.layout.row);
        spinnerAdapter3 = new ArrayAdapter<String>(this, R.layout.row);



        categoria.setAdapter(spinnerAdapter);
        moto.setAdapter(spinnerAdapter1);

        nazione.setAdapter(spinnerAdapter2);
        circuito.setAdapter(spinnerAdapter3);

        queryCat = "SELECT DISTINCT Categoria FROM Moto";
        queryNaz = "SELECT DISTINCT Nazione FROM Circuito";

        final DatabaseHelper gdh = new DatabaseHelper(getBaseContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = gdh.getReadableDatabase();

        Cursor c = db.rawQuery(queryCat, null);
        Cursor e = db.rawQuery(queryNaz, null);

        /*Carico la lista con le classi. Se premo una classe vedo tutti i compiti salvati*/
        while (c.moveToNext()) {
            categorie.add(c.getString(0));

        }
        spinnerAdapter.addAll(categorie);

        while (e.moveToNext()) {
            nazioni.add(e.getString(0));
        }
        spinnerAdapter2.addAll(nazioni);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                elencoMoto = new ArrayList<String>();
                TextView txt = (TextView) arg1.findViewById(R.id.rowtext);
                String s = txt.getText().toString();

                SQLiteDatabase db = gdh.getWritableDatabase();

                String selectQuery = "SELECT Marca, Modello, Cilindrata  FROM Moto WHERE Categoria = '" + s + "' ";
                correctMoto = 0;
                Cursor d = db.rawQuery(selectQuery, null);
                int x = d.getCount();

                for (int i = 0; i < x; i++) {
                    d.moveToNext();
                    dati = d.getString(0) + " " + d.getString(1) + " " + d.getString(2) + "cc";
                    elencoMoto.add(dati);
                }

                spinnerAdapter1.clear();
                spinnerAdapter1.add("--Scegli--");
                spinnerAdapter1.addAll(elencoMoto);
                d.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        moto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                TextView txt = (TextView) arg1.findViewById(R.id.rowtext);
                s = txt.getText().toString();
                if (s != "--Scegli--") {
                    motoQuery = s;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        db.close();


        nazione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                elencoCircuiti = new ArrayList<String>();
                TextView txt = (TextView) arg1.findViewById(R.id.rowtext);
                String s = txt.getText().toString();

                SQLiteDatabase db = gdh.getWritableDatabase();

                String selectQuery = "SELECT Nome, Nazione, Lunghezza  FROM Circuito WHERE Nazione = '" + s + "' ";
                correctCircuito = 0;
                Cursor d = db.rawQuery(selectQuery, null);
                int x = d.getCount();

                for (int i = 0; i < x; i++) {
                    d.moveToNext();
                    stringa = d.getString(0) + " " + d.getString(1) + " " + d.getString(2) + "m";
                    elencoCircuiti.add(stringa);
                }

                spinnerAdapter3.clear();
                spinnerAdapter3.add("--Scegli--");
                spinnerAdapter3.addAll(elencoCircuiti);
                d.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        circuito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                TextView txt = (TextView) arg1.findViewById(R.id.rowtext);
                s = txt.getText().toString();
                if (s != "--Scegli--") {
                    circuitoQuery = s;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        db.close();

        //BOTTONE PER CARICARE I DATI
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = User.id;
                User.circuito = circuitoQuery;
                User.moto = motoQuery;
                //if (!User.circuito.equalsIgnoreCase("--Scegli--") && !User.moto.equalsIgnoreCase("--Scegli--")){
                if (User.circuito !=null && User.moto != null){
                    System.out.println("ID: " + id + "MOTO: " + User.moto + "CIRCUITO: " + User.circuito);
                    Intent i = new Intent(getApplicationContext(), LocationFinder.class);
                    startActivity(i);
                }
                else {

                }

            }
        });

    }


    public static class ResoContoDati extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reso_conto_dati);
        }
    }
}
