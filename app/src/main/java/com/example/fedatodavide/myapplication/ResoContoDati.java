package com.example.fedatodavide.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fedatodavide.myapplication.LogicDatabase.User;

import java.util.ArrayList;

public class ResoContoDati extends AppCompatActivity {

    ListView lista;
    ArrayAdapter<String> adapter;
    ArrayList<String> tempi;
    TextView datiPilota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reso_conto_dati);
        datiPilota = (TextView) findViewById(R.id.textView5);
        datiPilota.setText("Id : " + User.id + " Moto : " + User.moto + " Circuito : " + User.circuito);
        tempi = new ArrayList<String>();
        tempi.add("11111");
        lista = (ListView) findViewById(R.id.listViewTempi);
        adapter = new ArrayAdapter<String>(this, R.layout.rowlist, tempi);
        lista.setAdapter(adapter);
        //adapter.add("ciao");

    }
}
