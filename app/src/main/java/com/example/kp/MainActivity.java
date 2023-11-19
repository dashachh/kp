package com.example.kp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText loginUsernameText;
    EditText loginPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginUsernameText = findViewById(R.id.editTextLogin);
        loginPasswordText = findViewById(R.id.editTextPassword);
    }

    public void loginToJira(View view) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);
    }
}