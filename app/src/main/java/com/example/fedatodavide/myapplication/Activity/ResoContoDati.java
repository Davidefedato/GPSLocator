package com.example.fedatodavide.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedatodavide.myapplication.LogicDatabase.OnClientMessageRead;
import com.example.fedatodavide.myapplication.LogicDatabase.ReadURL;
import com.example.fedatodavide.myapplication.LogicDatabase.User;
import com.example.fedatodavide.myapplication.R;

public class ResoContoDati extends AppCompatActivity {

    //DICHIARAZIONE DELLE VARIABILI
    ListView lista;
    ArrayAdapter<String> adapter;
    TextView datiPilota;
    Button elimina;
    Button caricaDB;
    int indirizzo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reso_conto_dati);
        Log.i("pos", String.valueOf(indirizzo));
        caricaDB = (Button) findViewById(R.id.btnCarica);
        elimina = (Button) findViewById(R.id.btnElimina);
        datiPilota = (TextView) findViewById(R.id.textView5);
        datiPilota.setText("Username : " + User.username + "\nMoto : " + User.moto + "\nCircuito : " + User.circuito);
        lista = (ListView) findViewById(R.id.listViewTempi);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, User.tempi);
        lista.setAdapter(adapter);

        //METODO CHE PERMETTE DI RICAVARE L'INDIRIZZO DELL'ELEMENTO PREMUTO SULLA LISTA
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indirizzo = position;
                Log.i("pos", String.valueOf(indirizzo));
            }
        });


        //QUANDO PREMUTO ELIMINA L'ELEMENTO SELEZIONATO DELLA LISTA
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!User.tempi.isEmpty() && indirizzo >= 0){
                    User.tempi.remove(indirizzo);
                    adapter.notifyDataSetChanged();
                }
                else{
                    if (User.tempi.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Non ci sono elementi da eliminare", Toast.LENGTH_LONG).show();
                    }
                    else if (indirizzo<0) {
                        Toast.makeText(getApplicationContext(), "Non hai selezionato l'elemento da eliminare", Toast.LENGTH_LONG).show();
                    }
                }
                indirizzo = -1;
            }
        });

        //BOTTONE PER EFFETTUARE IL CARICAMENTO DEI DATI SUL DATABASE
        caricaDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!User.tempi.isEmpty()){
                    //GENERA LA QUERY PER INSERIRE I DATI NEL DATABASE
                    ReadURL readURL = new ReadURL("http://davide17.altervista.org/TESINA/caricaDBprova.php?idMotociclista=" + User.id + "&tempo=" + User.tempi.toString().replace(" ", "%20") + "&moto=" + User.moto.replace(" ", "%20") + "&circuito=" + User.circuito.replace(" ", "%20"));
                    readURL.onClientMessageRead = new OnClientMessageRead() {
                        @Override
                        public void onMessageRead(final String message) {
                            System.out.println("Ricevo: " + message);
                            //CONTROLLA SE IL CARICAMENTO E' AVVENUTO CON SUCCESSO O NO
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (message.equalsIgnoreCase("Caricamento fallito")) {
                                        Toast.makeText(getApplicationContext(), "Caricamento fallito", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Caricamento avvenuto con successo", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    };
                    readURL.start();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Non ci sono valori da caricare", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //FUNZIONE CHE LANCIA SEMPRE L'ACTIVITY DI LOGIN QUANDO PREMO IL TASTO INDIETRO
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        User.tempi.clear();
        adapter.notifyDataSetChanged();
        indirizzo = -1;
    }
}
