package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String usernamePassed;
    String userName;

    DatabaseHelper db;

    TextView textViewQuote;
    TextView textViewFilm;
    Quotes quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //username
        final Intent intentP = getIntent();
        usernamePassed = intentP.getStringExtra("username");

        //QUOTES
        quotes = new Quotes();

        textViewQuote = findViewById(R.id.textViewQuote);
        textViewFilm = findViewById(R.id.textViewFilm);

        textViewQuote.setText("'" + quotes.getQuote() + "'");
        textViewFilm.setText("Dal film: " + quotes.getFilmName());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quotes = new Quotes();
                textViewQuote.setText("'" + quotes.getQuote() + "'");
                textViewFilm.setText("Dal film: " + quotes.getFilmName());
            }
        });

        textViewFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mettere il vero url
                String urlDownload = "https://play.google.com/store/apps/details?id=com.supercell.brawlstars";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String object = "CineTime";
                String body = "Hey, grazie all'app CineTime ho scoperto questa citazione del film '" + textViewFilm.getText().subSequence(10, textViewFilm.getText().length()) + "', volevo condividerla con te:\n" + textViewQuote.getText() + "\n\nScaricala anche tu da " + urlDownload;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, object);
                shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(shareIntent, "Condividi"));
            }
        });

        //Set nomeUtente WelcomeView
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Bentornato " + usernamePassed + "!");
    }


    @Override
    public void onBackPressed() {
        //se l'utente premerà il back button non gli sarà concesso di tornare indietro
        //empty
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;

            case R.id.profile:
                //passo l'username al profilo per farlo visualizzare
                Intent intent = new Intent(MainActivity.this, ProfiloActivity.class);
                intent.putExtra("username", usernamePassed);
                startActivity(intent);
                break;

            case R.id.serieTvPreferite:
                //passo l'username al profilo per farlo visualizzare
                Intent intentUserFav = new Intent(MainActivity.this, FavouriteSerieActivity.class);
                intentUserFav.putExtra("username", usernamePassed);
                startActivity(intentUserFav);
                break;

            case R.id.info:
                startActivity(new Intent(MainActivity.this, Info.class));
                break;

            case R.id.ricerca:
                Intent intentUser = new Intent(MainActivity.this, SearchActivity.class);
                intentUser.putExtra("username", usernamePassed);
                startActivity(intentUser);
                break;

            case R.id.nav_share:
                //TODO urlDownload da cambiare con quello che sarà il vero url
                String urlDownload = "https://play.google.com/store/apps/details?id=com.supercell.brawlstars";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String object = "CineTime";
                String body = "Sto utilizzando l'app CineTime, scaricala anche tu da " + urlDownload + " e lascia una recensione.";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, object);
                shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(shareIntent, "Condividi"));
                break;

            case R.id.nav_send:
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                break;

            case R.id.trends:
                startActivity(new Intent(MainActivity.this, Trends.class));
                break;

            case R.id.logout:
                //prendo username dalla login
                Intent prendoUserName = getIntent();
                userName = prendoUserName.getStringExtra("username");
                //setta a true l'utente loggato
                db.setStatusUser(0, userName);
                /*int check;
                check = db.setStatusUser(0, userName);
                if (check != 0) {
                    Toast.makeText(MainActivity.this, "SETTATO A LOGOUT", Toast.LENGTH_SHORT).show();
                }*/
                Intent out = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(out);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
