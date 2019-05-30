package com.example.myapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if (res == true) {
                    //setta a true l'utente loggato
                    int check;
                    db.setStatusUser(1, user);
                    check = db.setStatusUser(1, user);
                    if (check != 0) {
                        Toast.makeText(LoginActivity.this, "SETTATO A LOGGATO", Toast.LENGTH_SHORT).show();
                    }
                    Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                    View v = LoginActivity.this.getLayoutInflater().inflate(R.layout.progressbar, null);
                    dialog.setContentView(v);
                    dialog.show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Credenziali errate.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //impossibilita il tornare indietro (ad esempio dopo il logOut che lo riporta alla logIn)
    }

    public void showProgressingView() {
        if (!isProgressShowing) {
            View view = findViewById(R.id.progressBar1);
            view.bringToFront();
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

}