package com.example.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    String posterUrl;
    ImageButton aggiungiAiPreferitiBtn;
    boolean preferiti = false;
    DatabaseHelper db;
    String userNamePassed;
    int id;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();
        userNamePassed = intent.getStringExtra("username");

        title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        posterUrl = intent.getStringExtra("urlImg");
        String releaseDate = intent.getStringExtra("date");
        Double ratings = intent.getDoubleExtra("rating", 0.0);
        int numVoti = intent.getIntExtra("vote_count", 0);
        id = intent.getIntExtra("ids", 0);

        //bottone per aggiungere ai preferiti
        db = new DatabaseHelper(this);

        aggiungiAiPreferitiBtn = (ImageButton) findViewById(R.id.aggiungiAiPreferiti);
        if (isFavourite()) {
            this.preferiti = true;
            aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

        } else {
            this.preferiti = false;
            aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }
        aggiungiAiPreferitiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageFavourites();
            }
        });

        final ImageView imgPosterDetail = findViewById(R.id.imgPosterDetail);
        TextView txtTitleDetail = findViewById(R.id.txtTitleDetail);
        TextView txtOverView = findViewById(R.id.txtOverView);

        TextView txtRatings = findViewById(R.id.ratings);
        TextView txtReleaseDateDetail = findViewById(R.id.txtReleaseDateDetail);
        TextView titoloTolbar = findViewById(R.id.toolbar_title);

        txtTitleDetail.setText(title);
        if (overview.isEmpty()) {
            txtOverView.setText(R.string.noDescription);
        } else {
            txtOverView.setText(overview);
        }

        if (numVoti == 1) {
            txtRatings.setText("Rating: " + ratings.toString() + "/10 (1 voto)");
        } else {
            txtRatings.setText("Rating: " + ratings.toString() + "/10 (" + String.valueOf(numVoti) + " voti)");
        }

        txtReleaseDateDetail.setText("Anno: " + releaseDate);

        titoloTolbar.setText(title);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + posterUrl).fit().centerInside().into(imgPosterDetail);
        //full screen view for poster
        imgPosterDetail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent fullScreenIMG = new Intent(DetailsActivity.this, FullScreenImg.class);
                fullScreenIMG.putExtra("imgUrl", "https://image.tmdb.org/t/p/w500" + posterUrl);

                startActivity(fullScreenIMG);
            }
        });

    }

    private boolean isFavourite() {
        db.getWritableDatabase();
        Cursor cursor = db.checkPresenceInFavourites(userNamePassed, id);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            //presente
            // Log.d("PRESENTE", "SI " + String.valueOf(cursor.getCount()));
            return true;
        } else {
            //assente
            //Log.d("PRESENTE", "NO " + String.valueOf(cursor.getCount()));
            return false;
        }
    }

    private void manageFavourites() {
        if (!preferiti) {
            //cambio img btn in cuore pieno
            long val = db.addToFavorites(userNamePassed, id, title, posterUrl);
            if (val > 0) {
                aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                preferiti = true;
                Toast.makeText(DetailsActivity.this, "Aggiunto ai preferiti.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "Impossibile aggiungere ai preferiti, riprovare.", Toast.LENGTH_SHORT).show();
            }
        } else {
            //cambio img btn in cuore vuoto
            int nRowDeleted = db.removeFromFavorites(userNamePassed, id);
            if (nRowDeleted > 0) {
                aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                preferiti = false;
                Toast.makeText(DetailsActivity.this, "'" + title + "'" + " rimosso dai preferiti.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "Impossibile rimuovere dai preferiti, riprovare.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
