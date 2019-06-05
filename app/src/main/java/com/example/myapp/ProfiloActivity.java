package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapp.Database.DatabaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfiloActivity extends AppCompatActivity {
    CircleImageView profileIMG;
    DatabaseHelper dbManager;
    TextView userNameUtente;
    private int RESULT_LOAD_IMAGE = 123;
    private String PREFS_NAME = "image";
    private Context mContext;
    String path;

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
        path = getPreference(mContext, "imagePath");

        try {
            path = getPreference(mContext, "imagePath");

            if (path == null || path.length() == 0 || path.equalsIgnoreCase("")) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            } else {
                profileIMG.setImageBitmap(getScaledBitmap(path, 800, 800));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnModificaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = getPreference(mContext, "imagePath");

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //check
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                setPreference(mContext, picturePath, "imagePath");
                profileIMG.setImageBitmap(getScaledBitmap(picturePath, 800, 800));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean setPreference(Context c, String value, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    String getPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(key, "");
        return value;
    }

    //miglioramento img
    private Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = null;
        try {
            sizeOptions = new BitmapFactory.Options();
            sizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, sizeOptions);

            int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

            sizeOptions.inJustDecodeBounds = false;
            sizeOptions.inSampleSize = inSampleSize;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        int inSampleSize = 0;
        try {
            final int height = options.outHeight;
            final int width = options.outWidth;
            inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // set ratio
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width
                        / (float) reqWidth);

                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inSampleSize;
    }

    private int setWatchedFilms() {
        dbManager.getWritableDatabase();
        Cursor cursor = dbManager.getAllFavourites(userNameUtente.getText().toString());

        return cursor.getCount();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
