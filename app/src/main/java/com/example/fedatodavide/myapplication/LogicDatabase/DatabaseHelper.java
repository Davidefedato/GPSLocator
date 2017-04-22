package com.example.fedatodavide.myapplication.LogicDatabase;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "fedato.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Creo tutte le tabelle

    public void onCreate(SQLiteDatabase db) {
        Log.d("d",DatabaseContract.LevelEntry.SQL_CREATE);
        Log.d("d",DatabaseContract.LevelEntry1.SQL_CREATE1);

        db.execSQL(DatabaseContract.LevelEntry.SQL_CREATE);
        db.execSQL(DatabaseContract.LevelEntry1.SQL_CREATE1);

        // Aggiungo i dati standard nel database caricandoli da file
        BufferedReader br = null;
        try {
            AssetManager asManager = context.getAssets();
            br = new BufferedReader(new InputStreamReader(asManager.open("data.sql")), 1024 * 4);//mettere nome file corretto
            String line = null;
            db.beginTransaction();
            while ((line = br.readLine()) != null) {
                if(line.equals(""))
                    break;
                System.out.println(line);
                db.execSQL(line);
            }
            db.setTransactionSuccessful();
        } catch (IOException e) {
            Log.e("dberror", "read database init file error");
        } finally {
            db.endTransaction();
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Log.e("SQLError", "buffer reader close error");
                }
            }
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.d("dbUpgrade", "Upgrading db");
        db.execSQL(DatabaseContract.LevelEntry.SQL_DROPTABLE);
        db.execSQL(DatabaseContract.LevelEntry1.SQL_DROPTABLE1);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}