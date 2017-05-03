package com.example.fedatodavide.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fedatodavide.myapplication.LogicDatabase.User;

import java.util.ArrayList;

public class ResoContoDati extends AppCompatActivity {

    ListView lista;
    ArrayAdapter<String> adapter;
    ArrayList<String> tempi;
    TextView datiPilota;
    Button elimina;
    int indirizzo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reso_conto_dati);


        elimina = (Button) findViewById(R.id.btnElimina);
        datiPilota = (TextView) findViewById(R.id.textView5);
        datiPilota.setText("Id : " + User.id + " Moto : " + User.moto + " Circuito : " + User.circuito);
        lista = (ListView) findViewById(R.id.listViewTempi);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, User.tempi);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indirizzo = position;
            }
        });
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("pos", String.valueOf(indirizzo));
                User.tempi.remove(indirizzo);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
