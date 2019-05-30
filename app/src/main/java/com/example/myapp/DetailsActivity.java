package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    String posterUrl;
    ImageButton aggiungiAiPreferitiBtn;
    ListView listFavoriteView;
    //set a true se premuto aggiungi a preferiti
    boolean preferiti = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();

        final String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        posterUrl = intent.getStringExtra("urlImg");
        String releaseDate = intent.getStringExtra("date");
        Double ratings = intent.getDoubleExtra("rating", 0.0);
        int numVoti = intent.getIntExtra("vote_count", 0);
        final int id = intent.getIntExtra("ids", 0);

        //bottone per aggiungere ai preferiti
        aggiungiAiPreferitiBtn = (ImageButton) findViewById(R.id.aggiungiAiPreferiti);
        aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        aggiungiAiPreferitiBtn.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          if (!preferiti) {
                                                              //per cambiare il cuore vuoto in cuore pieno + TODO aggiungere ai preferiti
                                                              aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                                                              preferiti = true;



                                                               /*ArrayList<ElementoFilmSerie> titoloPref = new ArrayList<ElementoFilmSerie>();
                                                               ElementoFilmSerie elemento = new ElementoFilmSerie(id,title,posterUrl);
                                                               titoloPref.add(elemento);*/
                                                            /*  Intent passData = new Intent(DetailsActivity.this, FavouriteSerieActivity.class);
                                                              passData.putExtra("id", id);
                                                              passData.putExtra("title", title);
                                                              passData.putExtra("poster", posterUrl);
                                                              startActivity(passData);*/
                                                          } else {
                                                              //per cambiare il cuore pieno in cuore vuoto + TODO eliminare dai preferiti
                                                              aggiungiAiPreferitiBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                                                              preferiti = false;
                                                          }
                                                      }
                                                  }
        );

        final ImageView imgPosterDetail = findViewById(R.id.imgPosterDetail);
        TextView txtTitleDetail = findViewById(R.id.txtTitleDetail);
        TextView txtOverView = findViewById(R.id.txtOverView);
        /*TextView txtNumVoti = findViewById(R.id.votes);
         */
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


}
