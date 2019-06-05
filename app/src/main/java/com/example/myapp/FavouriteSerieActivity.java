package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;
import com.squareup.picasso.Picasso;

public class FavouriteSerieActivity extends AppCompatActivity {
    DatabaseHelper db;
    String username;

    String titoli[];
    String postersUrl[];
    int ids[];

    Context context;
    ListView listViewFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_serie);


        this.context = this;
        listViewFavourite = findViewById(R.id.listViewFavourites);

        //Recupero i preferiti dal Db in base all'utente loggato
        db = new DatabaseHelper(this);
        getLoggedUser();
        getFavourites();

        //Creazione adapter per la view passandogli param durante la req
        MySearchAdapter mySearchAdapter = new MySearchAdapter(context, titoli, postersUrl);
        listViewFavourite.setAdapter(mySearchAdapter);
        listViewFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int remove = db.removeFromFavorites(username, ids[position]);
                if (remove > 0) {
                    //Return num di row eliminate dalla delete
                    Toast.makeText(FavouriteSerieActivity.this, "'" + titoli[position] + "'" + " rimosso dai preferiti.", Toast.LENGTH_SHORT).show();
                    //Riaggiorno la view dopo averlo rimosso
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(FavouriteSerieActivity.this, "Impossibile rimuovere dai preferiti, riprovare.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageButton imgBtnBack = findViewById(R.id.backFavourite);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getLoggedUser() {
        db.getWritableDatabase();
        Cursor cursor = db.getUtente(1);
        cursor.moveToFirst();

        //setto var che sarà passata alla textView Welcome
        username = (cursor.getString(0));
    }

    private void getFavourites() {
        db.getWritableDatabase();
        Cursor cursor = db.getAllFavourites(username);
        cursor.moveToFirst();

        //Setto dimensione degli array che contengono i dati degli elementi
        // aggiunti ai preferiti in base al num di elementi presenti nel cursor
        ids = new int[cursor.getCount()];
        titoli = new String[cursor.getCount()];
        postersUrl = new String[cursor.getCount()];

        //Instanzio ciclo for per settare tutti i singoli elementi degli array locali
        // che saranno passati all'adapter per la visualizzazione
        for (int k = 0; k < cursor.getCount(); k++) {

            ids[k] = cursor.getInt(0);
            titoli[k] = cursor.getString(1);
            postersUrl[k] = cursor.getString(2);

            cursor.moveToNext();

        }
    }

    //Handler assegnazione risorse alla view
    //mettere qui i valori da passare alla visualizzazione
    class MySearchAdapter extends ArrayAdapter {
        //Variabili d'appoggio INTERNE che saranno setatte in base a quelle che gli vengono passate come param

        Context context;
        String rTitle[];
        String rImgs[];
        String urlImg;

        MySearchAdapter(Context c, String title[], String imgs[]) {
            super(c, R.layout.row_fav, R.id.titoloFavourites, title);
            //Avvaloro var internte con quelle passate così da assegnarle facilmente ai vari componenti della view
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowFav = layoutInflater.inflate(R.layout.row_fav, parent, false);

            //Referenziazione oggetti della view
            ImageView images = rowFav.findViewById(R.id.immagineFavourites);
            TextView myTitle = rowFav.findViewById(R.id.titoloFavourites);

            //Setting risorse della view
            myTitle.setText(rTitle[position]);

            urlImg = "https://image.tmdb.org/t/p/w500" + rImgs[position];
            if (rImgs[position].equals("null")) {
                images.setImageResource(R.drawable.no_img_movie);
            } else {
                Picasso.get().load(urlImg).into(images);
            }
            Picasso.get().load(urlImg).into(images);
            return rowFav;
        }
    }

}







