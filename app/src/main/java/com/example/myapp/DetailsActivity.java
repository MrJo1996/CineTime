package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    String posterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        posterUrl = intent.getStringExtra("urlImg");
        String releaseDate = intent.getStringExtra("date");
        Double ratings = intent.getDoubleExtra("rating", 0.0);

        final ImageView imgPosterDetail = findViewById(R.id.imgPosterDetail);
        TextView txtTitleDetail = findViewById(R.id.txtTitleDetail);
        TextView txtOverView = findViewById(R.id.txtOverView);

        TextView txtRatings = findViewById(R.id.txtRatings);
        TextView txtReleaseDateDetail = findViewById(R.id.txtReleaseDateDetail);
        TextView titoloTolbar = findViewById(R.id.toolbar_title);

        txtTitleDetail.setText(title);
        if (overview.isEmpty()) {
            txtOverView.setText(R.string.noDescription);
        } else {
            txtOverView.setText(overview);
        }

        txtRatings.setText(ratings.toString() + "/10 ");

        txtReleaseDateDetail.setText("Data rilascio: " + releaseDate);

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
