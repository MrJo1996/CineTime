package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    JSONArray jsonArray;

    Context context;
    ListView listView;
    EditText searchEditText;
    ImageButton imgBtnSearch;

    //var titolo Film o Serie
    String titoli[];
    String descrizioni[];
    String postersUrl[];
    String dateRilascio[];
    Double ratings[];
    String urlReq;
    int numVoti[];
    /*int ids[];*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //imposto il context da passare durante la req
        this.context = this;
        //fetch data code req
        this.mQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.listViewSearch);
        searchEditText = findViewById(R.id.searchEdiText);
        imgBtnSearch = findViewById(R.id.btnSearch);

        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlReq = "https://api.themoviedb.org/3/search/multi?api_key=adf4e37d8d2e065dcfac0c49267b47db&language=it-IT&query="
                        + searchEditText.getText() + "&page=1&include_adult=false";
                //req
                jsonParse();
            }
        });

        //TODO set click su item listview, da spostare giu quando si fa la richiesta
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("title", titoli[position]);
                intent.putExtra("overview", descrizioni[position]);
                intent.putExtra("urlImg", postersUrl[position]);
                intent.putExtra("date", dateRilascio[position]);
                intent.putExtra("rating", ratings[position]);
                intent.putExtra("vote_count", numVoti[position]);
               /* intent.putExtra("id", ids[position]);*/

                startActivity(intent);
            }
        });
    }

    private void jsonParse() {
        //CREAZIONE RICHIESTA
        //essendo un JSon ho bisogno di un handler della richiesta
        //(se era un array di JSon avrei usato un altro apposito -> JsonArrayRequest)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlReq, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //risposta va a buon fine
                        try {
                            //"results" è  il nome dell'array JSon che contiene tutti gli oggetti JSon
                            jsonArray = response.getJSONArray("results");
                            //setto dimensione singoli array
                            titoli = new String[jsonArray.length()];
                            descrizioni = new String[jsonArray.length()];
                            postersUrl = new String[jsonArray.length()];
                            dateRilascio = new String[jsonArray.length()];
                            ratings = new Double[jsonArray.length()];
                            numVoti = new int[jsonArray.length()];
                            /*ids = new int[jsonArray.length()];*/
                            //ciclo for per settare i nostri oggetti
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < titoli.length; i++) {
                                    if (jsonArray.getJSONObject(i).has("name")) {
                                        titoli[i] = jsonArray.getJSONObject(i).getString("name");
                                    } else {
                                        titoli[i] = jsonArray.getJSONObject(i).getString("title");
                                    }
                                    descrizioni[i] = (jsonArray.getJSONObject(i).getString("overview"));
                                    postersUrl[i] = (jsonArray.getJSONObject(i).getString("poster_path"));
                                    if (jsonArray.getJSONObject(i).has("first_air_date")) {
                                        dateRilascio[i] = (jsonArray.getJSONObject(i).optString("first_air_date"));
                                    } else {
                                        dateRilascio[i] = (jsonArray.getJSONObject(i).optString("release_date"));
                                    }
                                    ratings[i] = (jsonArray.getJSONObject(i).getDouble("vote_average"));
                                    numVoti[i] = (jsonArray.getJSONObject(i).getInt("vote_count"));
                                    /*ids[i] = (jsonArray.getJSONObject(i).getInt("id"));*/
                                }
                            } else {
                                //Controllo se c'è o meno un risultato per la ricerca
                                Toast.makeText(SearchActivity.this, "Nessun risultato trovato per tale titolo.", Toast.LENGTH_LONG).show();
                            }
                            //creazione adapter per la view passandogli param durante la req, prima dava NPE(non avvalorava gli array)
                            MySearchAdapter mySearchAdapter = new MySearchAdapter(context, titoli, descrizioni, postersUrl);
                            listView.setAdapter(mySearchAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //risposta va male
                Toast.makeText(SearchActivity.this, "Qualcosa è andato storto, controlla la tua connessione internet", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        //add req alla queue
        mQueue.add(request);
    }

    //handler assegnazione risorse alla view
    // mettere qui i valori da passare alla visualizzazione
    class MySearchAdapter extends ArrayAdapter {
        //variabili d'appoggio INTERNE che saranno setatte in base a quelle che gli vengono passate come param
        //il passagio avviene durante l'esecuzione della req
        Context context;
        String rTitle[];
        String rDesc[];
        String rImgs[];
        String urlImg;

        MySearchAdapter(Context c, String title[], String description[], String imgs[]) {
            super(c, R.layout.row, R.id.titoloSearch, title);
            //avvaloro var internte con quelle passate così da assegnarle ai vari componenti della view
            this.context = c;
            this.rTitle = title;
            this.rDesc = description;
            this.rImgs = imgs;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            ImageView images = row.findViewById(R.id.immagineRicercaListView);
            TextView myTitle = row.findViewById(R.id.titoloSearch);
            TextView myDesc = row.findViewById(R.id.descrizioneSearch);

            //setting risorse della view
            myTitle.setText(rTitle[position]);

            if (rDesc[position].isEmpty()) {
                myDesc.setText(getString(R.string.noDescription));
            } else {
                myDesc.setText(rDesc[position]);
            }

            urlImg = "https://image.tmdb.org/t/p/w500" + rImgs[position];
            if (rImgs[position].equals("null")) {
                images.setImageResource(R.drawable.no_img_movie);
            } else {
                Picasso.get().load(urlImg).into(images);
            }
            return row;
        }
    }
}
