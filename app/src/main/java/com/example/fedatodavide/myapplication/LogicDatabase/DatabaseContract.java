package com.example.fedatodavide.myapplication.LogicDatabase;

import android.provider.BaseColumns;

public final class
        DatabaseContract {

    //CREAZIONE DEL COSTRUTTORE VUOTO
    public DatabaseContract() {
    }

    //DEFINISCE IL CONTENUTO DELLA TABELLA MOTO
    public static abstract class LevelEntry implements BaseColumns {
        public static final String TABLE_NAME = "Moto";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_MARCA = "Marca";
        public static final String COLUMN_NAME_MODELLO = "Modello";
        public static final String COLUMN_NAME_CILINDRATA = "Cilindrata";
        public static final String COLUMN_NAME_CATEGORIA = "Categoria";

        //SQL PER CREARE LA TABELLA MOTO
        static final String SQL_CREATE =
                "CREATE TABLE `Moto` (\n" +
                        "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "\t`Marca`\tTEXT,\n" +
                        "\t`Modello`\tTEXT,\n" +
                        "\t`Cilindrata`\tTEXT,\n" +
                        "\t`Categoria`\tTEXT\n" +
                        ");";

        //SQL PER ELIMINARE LA TABELLA MOTO
        static final String SQL_DROPTABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    //DEFINISCE IL CONTENUTO DELLA TABELLA CIRCUITO
    public static abstract class LevelEntry1 implements BaseColumns {
        public static final String TABLE_NAME = "Circuito";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOME = "Nome";
        public static final String COLUMN_NAME_NAZIONE = "Nazione";
        public static final String COLUMN_NAME_LUNGHEZZA = "Lunghezza";



        //SQL PER CREARE LA TABELLA CIRCUITO
        static final String SQL_CREATE1 =
                "CREATE TABLE `Circuito` (\n" +
                        "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "\t`Nome`\tTEXT,\n" +
                        "\t`Nazione`\tTEXT,\n" +
                        "\t`Lunghezza`\tTEXT\n" +
                        ");";

        //SQL PER ELIMINARE LA TABELLA CIRCUITO
        static final String SQL_DROPTABLE1 =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

}

