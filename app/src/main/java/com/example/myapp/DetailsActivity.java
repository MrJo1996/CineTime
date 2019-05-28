package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        String posterUrl = intent.getStringExtra("urlImg");
        String releaseDate = intent.getStringExtra("date");
        Double ratings = intent.getDoubleExtra("rating", 0.0);

        ImageView imgPosterDetail = findViewById(R.id.imgPosterDetail);
        TextView txtTitleDetail = findViewById(R.id.txtTitleDetail);
        TextView txtOverView = findViewById(R.id.txtOverView);

        TextView txtRatings = findViewById(R.id.txtRatings);
        TextView txtReleaseDateDetail = findViewById(R.id.txtReleaseDateDetail);
        Toolbar toolBarDetail = findViewById(R.id.toolbarDetail);

        txtTitleDetail.setText(title);
        if (overview.isEmpty()) {
            txtOverView.setText(R.string.noDescription);
        } else {
            txtOverView.setText(overview);
        }
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + posterUrl).fit().centerInside().into(imgPosterDetail);

        txtRatings.setText(ratings.toString() + "/10 ");

        txtReleaseDateDetail.setText(releaseDate);

        toolBarDetail.setTitle(title);
    }
}
