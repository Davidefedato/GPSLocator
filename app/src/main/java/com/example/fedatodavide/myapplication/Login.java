package com.example.fedatodavide.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fedatodavide.myapplication.LogicDatabase.OnClientMessageRead;
import com.example.fedatodavide.myapplication.LogicDatabase.ReadURL;
import com.example.fedatodavide.myapplication.LogicDatabase.User;

public class Login extends AppCompatActivity {


    Button registra;
    Button accedi;
    EditText username;
    EditText password;
    String u;
    String p;
    int id=0;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registra = (Button) findViewById(R.id.registra);
        accedi = (Button) findViewById(R.id.accedi);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = username.getText().toString();
                p = password.getText().toString();
                if (u.equalsIgnoreCase("") || p.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Mancano i campi obbligatori", Toast.LENGTH_LONG).show();
                    return;
                }

                ReadURL readURL = new ReadURL("http://davide17.altervista.org/TESINA/signin.php?username="+ u +"&password="+ p);
                readURL.onClientMessageRead = new OnClientMessageRead() {
                    @Override
                    public void onMessageRead(final String message) {

                        System.out.println("Ricevo: " + message);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (message.equalsIgnoreCase("utente già registrato")){
                                    Toast.makeText(getApplicationContext(),"L'utente esiste già", Toast.LENGTH_LONG).show();
                                }
                                else if (message.equalsIgnoreCase("fallito") || message.equalsIgnoreCase("mancano i dati")){
                                    Toast.makeText(getApplicationContext(),"Registrazione fallita", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    id = Integer.parseInt(message.toString());
                                    User.id = id;
                                    System.out.println("USER ID : " + User.id);
                                    Toast.makeText(getApplicationContext(),"Registrazione avvenuta con successo", Toast.LENGTH_LONG).show();
                                    User.username = u;
                                    //fai partire un'activity
                                    Intent intent = new Intent(getApplicationContext(),RaccoltaDati.class);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                };
                readURL.start();
            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = username.getText().toString();
                p = password.getText().toString();

                if (u.equalsIgnoreCase("") || p.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Mancano i campi obbligatori", Toast.LENGTH_LONG).show();
                    return;
                }

                ReadURL readURL = new ReadURL("http://davide17.altervista.org/TESINA/login.php?username="+ u +"&password="+ p);
                readURL.onClientMessageRead = new OnClientMessageRead() {
                    @Override
                    public void onMessageRead(final String message) {
                        System.out.println("Ricevo: " + message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (message.equalsIgnoreCase("Log in fallito")){
                                    Toast.makeText(getApplicationContext(),"L'utente non esiste", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    id = Integer.parseInt(message.toString());
                                    User.id = id;
                                    Toast.makeText(getApplicationContext(),"Log in avvenuto con successo", Toast.LENGTH_LONG).show();
                                    User.username = u;
                                    //fai partire un'activity
                                    Intent intent = new Intent(getApplicationContext(),RaccoltaDati.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                };
                readURL.start();
            }
        });

    }
}
