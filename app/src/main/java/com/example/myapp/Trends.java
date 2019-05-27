package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.Calendar;

public class Trends extends AppCompatActivity {
    private RequestQueue mQueue;
    JSONArray jsonArray;

    ListView listView;

    Context context;
    String titoli[];
    String descrizioni[];
    String postersUrl[];
    String dataRelease[];
    String urlReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //imposto il context da passare durante la req
        this.context = this;
        //fetch data code req
        this.mQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        listView = findViewById(R.id.listViewTrends);

     /*   Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        //req trends
        urlReq = "https://api.themoviedb.org/3/movie/upcoming?api_key=adf4e37d8d2e065dcfac0c49267b47db&language=it&region=it";
        jsonParse();

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
                            dataRelease = new String[jsonArray.length()];
                            //ciclo for per settare i nostri Json Oggetti
                            for (int i = 0; i < titoli.length; i++) {
                                titoli[i] = (jsonArray.getJSONObject(i).getString("title"));
                                descrizioni[i] = (jsonArray.getJSONObject(i).getString("overview"));
                                postersUrl[i] = (jsonArray.getJSONObject(i).getString("poster_path"));
                                dataRelease[i] = (jsonArray.getJSONObject(i).getString("release_date"));
                            }
                            //creazione adapter per la view passandogli param durante la req, prima dava NPE(non avvalorava gli array)
                            MyTrendsAdapter myTrendsAdapter = new MyTrendsAdapter(context, titoli, descrizioni, postersUrl, dataRelease);
                            listView.setAdapter(myTrendsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //risposta va male
                Toast.makeText(Trends.this, "Qualcosa è andato storto, controlla la tua connessione internet.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        //add req alla queue
        mQueue.add(request);
    }

    class MyTrendsAdapter extends ArrayAdapter {
        //variabili d'appoggio INTERNE che saranno setatte in base a quelle che gli vengono passate come param
        //il passagio avviene durante l'esecuzione della req
        Context context;
        String rTitle[];
        String rDesc[];
        String rImgs[];
        String urlImg;
        String rilascioData[];

        MyTrendsAdapter(Context c, String title[], String description[], String imgs[], String data[]) {
            super(c, R.layout.row_trends, R.id.titoloTrends, title);
            //avvaloro var internte con quelle passate così da assegnarle ai vari componenti della view
            this.context = c;
            this.rTitle = title;
            this.rDesc = description;
            this.rImgs = imgs;
            this.rilascioData = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row_trends = layoutInflater.inflate(R.layout.row_trends, parent, false);

            ImageView images = row_trends.findViewById(R.id.immagineTrends);
            final TextView myTitle = row_trends.findViewById(R.id.titoloTrends);
            TextView myDesc = row_trends.findViewById(R.id.descrizioneTrends);
            final TextView myDate = row_trends.findViewById(R.id.release_data);

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

            myDate.setText("Data uscita: " + dataRelease[position]);

            myDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(Integer.parseInt(myDate.getText().toString().substring(13, 17)), Integer.parseInt(myDate.getText().toString().substring(19, 20)) - 1, Integer.parseInt(myDate.getText().toString().substring(21, 23)), 12, 30);

                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Integer.parseInt(myDate.getText().toString().substring(13, 17)), Integer.parseInt(myDate.getText().toString().substring(19, 20)) - 1, Integer.parseInt(myDate.getText().toString().substring(21, 23)), 13, 0);

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, myTitle.getText())
                            .putExtra(CalendarContract.Events.DESCRIPTION, myTitle.getText() + " è al cinema,corri a vederlo!")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Cinema")
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                    startActivity(intent);
                }
            });

            return row_trends;
        }
    }
}
