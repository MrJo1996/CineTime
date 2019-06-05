package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfiloActivity extends AppCompatActivity {
    CircleImageView profileIMG;
    DatabaseHelper dbManager;
    TextView userNameUtente;

    Context mContext;
    SharedPreferences myPrefrence;
    String namePreferance = "name";
    String imagePreferance = "image";
    int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        userNameUtente = findViewById(R.id.userNameUtente);
        final TextView nomeUtente = findViewById(R.id.text_nomeUtenteProfilo);
        final TextView cognomeUtente = findViewById(R.id.cognomeUtenteprofilo);
        final TextView emailUtente = findViewById(R.id.textEmailProfilo);
        final TextView numFilm = findViewById(R.id.numFilmVisti);
        profileIMG = findViewById(R.id.profile_image);
        final ImageButton btnModificaFoto = findViewById(R.id.bottoneModificaFoto);
        final ImageButton btnShareNumFilm = findViewById(R.id.bottoneShareVisti);

        //RETRIVING DATA from SQLite DB
        dbManager = new DatabaseHelper(this);
        dbManager.getWritableDatabase();
        Cursor cursor = dbManager.getUtente(1);
        cursor.moveToFirst();

        //Set elementi del layout
        userNameUtente.setText(cursor.getString(0));
        nomeUtente.setText("Nome: " + cursor.getString(1));
        cognomeUtente.setText("Cognome: " + cursor.getString(2));
        emailUtente.setText("Email: " + cursor.getString(3));

        //Set numero film visti
        numFilm.setText(String.valueOf(setWatchedFilms()));

        mContext = this;

        btnShareNumFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlDownload = "https://drive.google.com/open?id=1Hj5TW2dYEy1ZeqJrJrGbNzTgSPRZ1-nF";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String object = "CineTime";
                String body = "Grazie all'app CineTime fino ad ora ho tenuto traccia di " + numFilm.getText().toString() + " film, scaricala anche tu da " + urlDownload + " e fammi sapere se riesci a fare di meglio.";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, object);
                shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(shareIntent, "Condividi"));
            }
        });

        ImageButton imgBtnBack = findViewById(R.id.backProfile);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnModificaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });

        //Setto immagine profilo se presente imgEncoded nelle SharedPref poichè
        // nell'onActivityResult salvo l'img convertendolo in stringa nelle sharedP
        String encodedImg = PreferenceManager.getDefaultSharedPreferences(mContext).getString("IMAGE", null);
        if (encodedImg != null) {
            Bitmap imgP = decodeBase64(encodedImg);
            profileIMG.setImageBitmap(imgP);
        } else {
            //null
        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                profileIMG.setImageBitmap(selectedImage);

                //Converto in Stringa
                String bitMapEncoded = encodeTobase64(selectedImage);
                //Salvo bitmap encoded e lo salvo nelle SharedPref
                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("IMAGE", bitMapEncoded).apply();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfiloActivity.this, "Qualcosa è andato storto, riprova.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ProfiloActivity.this, "Non hai selezionato alcuna immagine.", Toast.LENGTH_LONG).show();
        }
    }

    // Conversione bitmap
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    // Riconversione per il setting
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private int setWatchedFilms() {
        dbManager.getWritableDatabase();
        Cursor cursor = dbManager.getAllFavourites(userNameUtente.getText().toString());

        return cursor.getCount();
    }
}
