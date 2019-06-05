package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

//Facilitare visualizzazione full screen di un poster
public class FullScreenImg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_img);

        Intent intent = getIntent();

        String urlImg = intent.getStringExtra("imgUrl");

        ImageView imageViewFullScreen = findViewById(R.id.fullScreenIMG);

        if (urlImg.equals("null")) {
            imageViewFullScreen.setImageResource(R.drawable.no_img_movie);
        } else {
            Picasso.get().load(urlImg).into(imageViewFullScreen);
        }
    }

}
