package com.example.kp.activities;

import static com.example.kp.entities.Constants.getCurrentUserURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.entities.Requests;

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
        Requests.GetRequestAsync authRequest = new Requests.GetRequestAsync();
        try {
//            if (!Requests.GetRequestSync(getCurrentUserURL).equals(null)) {
            if (!authRequest.execute(getCurrentUserURL).get().equals(null)) {
                Intent intent = new Intent(this, AllTasks.class);
                startActivity(intent);
            }
        } catch (Exception exception) {
            Log.d("auth111", "fail");
        }
    }
}