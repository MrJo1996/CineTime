package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.database.CursorWindowCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;

public class ProfiloActivity extends AppCompatActivity {

    TextView nomeTextView;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        //RETRIVING DATA from SQLite DB
        DatabaseHelper dbManager = new DatabaseHelper(this);
        dbManager.getWritableDatabase();
        Cursor cursor = dbManager.getUtente(1);
        cursor.moveToFirst();
        final TextView nomeUtente = findViewById(R.id.nomeUtente);
        nomeUtente.setText(cursor.getString(3));


    }

}
