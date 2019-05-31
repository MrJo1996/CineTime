package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfiloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        final Intent intent = getIntent();
        final String userNamePassed = intent.getStringExtra("username");

        final TextView userNameUtente = findViewById(R.id.userNameUtente);
        final TextView nomeUtente = findViewById(R.id.text_nomeUtenteProfilo);
        final TextView cognomeUtente = findViewById(R.id.cognomeUtenteprofilo);
        final TextView emailUtente = findViewById(R.id.textEmailProfilo);

        final CircleImageView profileIMG = findViewById(R.id.profile_image);
        final Button btnModificaFoto = findViewById(R.id.bottoneModificaFoto);

        //RETRIVING DATA from SQLite DB
        DatabaseHelper dbManager = new DatabaseHelper(this);
        dbManager.getWritableDatabase();
        Cursor cursor = dbManager.getUtente(userNamePassed);
        cursor.moveToFirst();

        //Set elementi del layout
        userNameUtente.setText(cursor.getString(0));
        nomeUtente.setText(cursor.getString(1));
        cognomeUtente.setText(cursor.getString(2));
        emailUtente.setText(cursor.getString(3));

        btnModificaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Scatta o seleziona foto dalla galleria magari
            }
        });
    }


}
