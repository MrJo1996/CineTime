package com.example.myapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
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
        sqLiteDatabase.execSQL("CREATE TABLE favourite (idfavourite INTEGER, idUtente INTEGER, username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addUser(String user, String password, String nome, String cognome, String email, int logged) {
        SQLiteDatabase db = this.getWritableDatabase();

        //String query = ("INSERT INTO registeruser VALUES ("+
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        contentValues.put("nome", nome);
        contentValues.put("cognome", cognome);
        contentValues.put("email", email);
        contentValues.put("logged", logged);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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
        return this.getWritableDatabase().update("registeruser", contentValues, "username = " + pUsername, null);
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

}