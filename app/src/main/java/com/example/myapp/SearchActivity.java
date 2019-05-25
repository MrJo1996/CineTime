package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"Film1", " Film2", "Film3"};
    String mDesciption[] = {"Descrzione111111", "Descrzione2", "Descrzione3"};
    int images[] = {R.drawable.ic_favorite_black_24dp, R.drawable.ic_home_black_24dp, R.drawable.ic_menu_manage};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.listViewSearch);

        //creazione adapter per la view
        MySearchAdapter mySearchAdapter = new MySearchAdapter(this, mTitle, mDesciption, images);

        listView.setAdapter(mySearchAdapter);

        //set click su item listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(SearchActivity.this, ProfiloActivity.class));
                    Toast.makeText(SearchActivity.this, mDesciption[0], Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    Toast.makeText(SearchActivity.this, "descrizione 2", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Toast.makeText(SearchActivity.this, "descrizione 3", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //done item click

    }

    class MySearchAdapter extends ArrayAdapter {
        Context context;
        String rTitle[];
        String rDesc[];
        int rImgs[];

        MySearchAdapter(Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.titoloSearch, title);
            this.context = c;
            this.rTitle = title;
            this.rDesc = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.immagineRicercaListView);
            TextView myTitle = row.findViewById(R.id.titoloSearch);
            TextView myDesc = row.findViewById(R.id.descrizioneSearch);

            //setting risorse on view
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDesc.setText(rDesc[position]);


            return row;
        }
    }
}
