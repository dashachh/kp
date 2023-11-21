package com.example.kp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;

public class Authorization extends AppCompatActivity {

    EditText loginUsernameText;
    EditText loginPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        loginUsernameText = findViewById(R.id.editTextLogin);
        loginPasswordText = findViewById(R.id.editTextPassword);
    }

    public void loginToJira(View view) {
        Intent intent = new Intent(this, AllTasks.class);
        startActivity(intent);
    }
}