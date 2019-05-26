package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    JSONArray jsonArray;

    Context context;
    ListView listView;
    String testTitolo[];
    String testDesc[];
    int images[] = {R.drawable.ic_favorite_black_24dp, R.drawable.ic_home_black_24dp, R.drawable.ic_menu_manage, R.drawable.ic_menu_manage, R.drawable.ic_menu_manage, R.drawable.ic_menu_manage};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //imposto il context da passare durante la req
        this.context = this;
        //fetch data code req
        this.mQueue = Volley.newRequestQueue(this);
        //req
        jsonParse();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = findViewById(R.id.listViewSearch);

        //TODO set click su item listview, da spostare giu quando si fa la richiesta
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(SearchActivity.this, ProfiloActivity.class));
                    Toast.makeText(SearchActivity.this, "desc 1", Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    Toast.makeText(SearchActivity.this, "descrizione 2", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Toast.makeText(SearchActivity.this, "descrizione 3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void jsonParse() {
        //url della chiamata
        final String urlGOT = "https://api.themoviedb.org/3/search/tv?api_key=adf4e37d8d2e065dcfac0c49267b47db&query=Game%20of%20thrones";

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
                            jsonArray = response.getJSONArray("results");
                            //setto dimensione singoli array
                            testTitolo = new String[jsonArray.length()];
                            testDesc = new String[jsonArray.length()];

                            //ciclo for per settare i nostri Json Oggetti
                            for (int i = 0; i < testTitolo.length; i++) {
                                testTitolo[i] = (jsonArray.getJSONObject(i).getString("name"));
                                testDesc[i] = (jsonArray.getJSONObject(i).getString("overview"));

                                /* Log.d("STAMPA", "Titolo " + i + ":" + testTitolo[i] + "Descrizione " + i + ": " + testDesc[i]);
                                textViewResult.append(jsonArray.getJSONObject(i).getString("name") + ", "
                                        + jsonArray.getJSONObject(i).getInt("id") + ", "
                                        + jsonArray.getJSONObject(i).getString("first_air_date") + "\n\n");*/
                            }
                            //creazione adapter per la view passandogli param durante la req, prima dava NPE(non avvalorava gli array)
                            MySearchAdapter mySearchAdapter = new MySearchAdapter(context, testTitolo, testDesc, images);
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
        int rImgs[];

        MySearchAdapter(Context c, String title[], String description[], int imgs[]) {
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
            myDesc.setText(rDesc[position]);
            images.setImageResource(rImgs[position]);

            return row;
        }
    }
}
