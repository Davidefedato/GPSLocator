package com.example.fedatodavide.myapplication.LogicDatabase;

//INTERFACE DA IMPLEMENTARE NEL CODICE PER FARLA FUNZIONARE IN MODO CHE POSSA LEGGERE I MESSAGGI DAL SERVER, POICHE' DA SOLA NON FA NULLA
public interface OnClientMessageRead {
    void onMessageRead(String message);
}
