package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.ContactActivity;
import com.example.myapp.Database.DatabaseHelper;
import com.example.myapp.FavouriteSerieActivity;
import com.example.myapp.NotificationsActivity;
import com.example.myapp.ProfiloActivity;
import com.example.myapp.R;
import com.example.myapp.SearchActivity;
import com.example.myapp.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView textViewResult;
    private Button btnRequest;
    private Button btnNext;
    private RequestQueue mQueue;
    String usernameProva;
    String userName;
    DatabaseHelper db; //per impostare status a logout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //CODE
        this.textViewResult = findViewById(R.id.result);
        this.btnRequest = findViewById(R.id.btnRequest);
        this.btnNext = findViewById(R.id.btnNext);
        //= findViewById(R.id.btnNext);


        this.mQueue = Volley.newRequestQueue(this);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();

                /*Log.d("TEST","X " + String.valueOf(textViewResult));
                 */
            }
        });

        //start new activity by btn
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfiloActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //se l'utente premerà il back button non gli sarà concesso di tornare indietro
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
                intent.putExtra("username", usernameProva);
                startActivity(intent);
                break;

            case R.id.serieTvPreferite:
                startActivity(new Intent(MainActivity.this, FavouriteSerieActivity.class));
                break;

            case R.id.impostazioni:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;

            case R.id.ricerca:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;

            case R.id.notifica:
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                break;

            case R.id.nav_share:
                //TODO urlDownload da cambiare con quello che sarà il vero url
                String urlDownload = "https://play.google.com/store/apps/details?id=com.supercell.brawlstars";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String object = "Serie Time";
                String body = "Sto utilizzando l'app SerieTime, scaricala anche tu da " + urlDownload + " e lascia una recensione.";
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
                int check;
                db.setStatusUser(0, userName);
                check = db.setStatusUser(0, userName);
                Log.d("LOGGGGGGGGGG", "Out: " + userName);
                if (check != 0) {
                    Toast.makeText(MainActivity.this, "SETTATO A LOGOUT", Toast.LENGTH_SHORT).show();
                }
                Intent out = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(out);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CODE

    private void jsonParse() {
        /*
         * JsonObjectRequest takes in five paramaters
         * Request Type - This specifies the type of the request eg: GET,POST
         *
         * URL - This String param specifies the Request URL
         *
         * JSONObject - This parameter takes in the POST parameters."null" in
         * case of GET request.
         *
         * Listener -This parameter takes in a implementation of Response.Listener()
         * interface which is invoked if the request is successful
         *
         * Listener -This parameter takes in a implementation of Error.Listener()
         * interface which is invoked if any error is encountered while processing
         * the request */

        //url della chiamata
        String urlTitanic = "https://api.themoviedb.org/3/search/movie?api_key=adf4e37d8d2e065dcfac0c49267b47db&query=titanic";
        String urlGOT = "https://api.themoviedb.org/3/search/tv?api_key=adf4e37d8d2e065dcfac0c49267b47db&query=Game%20of%20thrones";
        //CREAZIONE RICHIESTA
        //essendo un JSon ho bisogno di un handler della richiesta
        //(se era un array di JSon avrei usato un altro apposito -> JsonArrayRequest)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlGOT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //risposta va a buon fine
                        try {
                            //"results" è  il nome dell'array JSon che contiene tutti gli oggetti JSon
                            //setto variabile che contterrà il risultato della chiamata
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d("TEST", "X: " + jsonArray.toString());
                            //ciclo for instaziato per settare i nostri Json Oggetti
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //var che conterrà i singoli employee

                                textViewResult.append(jsonArray.getJSONObject(i).getString("name") + ", "
                                        + jsonArray.getJSONObject(i).getInt("id") + ", "
                                        + jsonArray.getJSONObject(i).getString("first_air_date") + "\n\n");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //risposta va male
                error.printStackTrace();
            }
        });

        //aaggiunta della richiesta alla coda
        mQueue.add(request);
    }
}
