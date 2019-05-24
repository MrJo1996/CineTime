package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Codice per mostrare l'activity a schermo intero (il theme dell'xml deve essere impostato su "NOACTIONBAR")
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //SplashScreen CODE
        Thread splash = new Thread(){
           public void run(){
               try {
                   sleep(2500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               finally {
                   Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                   startActivity(intent);
               }
           }
        };
        splash.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
