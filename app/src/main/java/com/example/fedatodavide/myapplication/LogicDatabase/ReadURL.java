package com.example.fedatodavide.myapplication.LogicDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadURL extends Thread {

    public OnClientMessageRead onClientMessageRead;
    private String indirizzo;

    public ReadURL(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void run() {
        try {
            URL pagina = new URL(indirizzo);
            BufferedReader in = new BufferedReader(new InputStreamReader(pagina.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (onClientMessageRead != null)
                    onClientMessageRead.onMessageRead(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
