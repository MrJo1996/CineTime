package com.example.myapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String TABLE_FAV = "userFavourite";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "nome";
    public static final String COL_5 = "cognome";
    public static final String COL_6 = "email";
    public static final String COL_7 = "logged";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, nome TEXT, cognome TEXT, email TEXT, logged INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS userFavourite (idfavourite INTEGER, username TEXT, titoloFav TEXT, posterPathFav TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        //sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password, String nome, String cognome, String email, int logged) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        contentValues.put("nome", nome);
        contentValues.put("cognome", cognome);
        contentValues.put("email", email);
        contentValues.put("logged", logged);

        // insert row
        long res = db.insert("registeruser", null, contentValues);

        db.close();

        // return inserted row id
        return res;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select ID from registeruser where " + COL_2 + "='" + username + "'" + " and " + COL_3 + "='" + password + "'", null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public int setStatusUser(int pLog, String pUsername) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("logged", pLog);
        return this.getWritableDatabase().update("registeruser", contentValues, "username ='" + pUsername + "'", null);
    }

    public Cursor getUtente(int pLog) {
        Cursor cursor = this.getWritableDatabase().query("registeruser", new String[]{"username", "nome", "cognome", "email"}, "logged = " + pLog, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //restituirà tutti i campi forniti nella query e saranno accessibili tramite indice di colonna
        // (ordine definito durante la query) - Esempio di utilizzo in "ProfileActivity"

        return cursor;
    }

    /*
     *
     * GESTIONE PREFERITI
     *
     */
    public long addToFavorites(String pUserName, int pIdTitolo, String pTitoloFav, String pPosterPath) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", pUserName);
        contentValues.put("idfavourite", pIdTitolo);
        contentValues.put("titoloFav", pTitoloFav);
        contentValues.put("posterPathFav", pPosterPath);
        // insert row - ritorna l'id della row inserita (-1 se c'è errore)
        long res = db.insert("userFavourite", null, contentValues);
        db.close();
        // return newly inserted row id
        return res;
    }

    public int removeFromFavorites(String pUserName, int pIdTitolo) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("userFavourite", "idfavourite = ? AND username = ?", new String[]{String.valueOf(pIdTitolo), pUserName});
        db.close();

        // return numero di righe eliminate
        return result;
    }

    public Cursor getAllFavourites(String pUserName) {
        Cursor cursor = this.getWritableDatabase().query("userFavourite", new String[]{"idfavourite", "titoloFav", "posterPathFav"}, "username ='" + pUserName + "'", null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //restituirà tutti i campi forniti nella query e saranno accessibili tramite indice di colonna
        // (ordine definito durante la query) - Esempio di utilizzo in "ProfileActivity"

        return cursor;
    }

    public Cursor checkPresenceInFavourites(String pUserName, int pId) {
        Cursor cursor = this.getWritableDatabase().query("userFavourite", new String[]{"idfavourite"}, "username ='" + pUserName + "'" + " and " + "idfavourite = " + String.valueOf(pId), null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        //restituirà tutti i campi forniti nella query e saranno accessibili tramite indice di colonna
        // (ordine definito durante la query) - Esempio di utilizzo in "DetailsActivity"

        return cursor;
    }

}