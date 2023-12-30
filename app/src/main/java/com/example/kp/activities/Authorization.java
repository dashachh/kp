package com.example.kp.activities;

import static com.example.kp.entities.Constants.getCurrentUserURL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.entities.Requests;

import java.util.Base64;

public class Authorization extends AppCompatActivity {

    EditText loginUsernameText;
    EditText passwordUsernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        loginUsernameText = findViewById(R.id.editTextLogin);
        passwordUsernameText = findViewById(R.id.editTextPassword);
    }

    public void loginToJira(View view) {
        String loginUsername = loginUsernameText.getText().toString();
        String passwordUsername = passwordUsernameText.getText().toString();
        if (!loginUsername.isEmpty() && !passwordUsername.isEmpty()) {
            String encodedToken = "Basic " + Base64.getEncoder().encodeToString((loginUsername + ":" + passwordUsername).getBytes());
            Requests.GetRequestAsync authRequest = new Requests.GetRequestAsync();
            try {
                if (!authRequest.execute(encodedToken, getCurrentUserURL).get().equals(null)) {
                    loginUsernameText.setText("");
                    passwordUsernameText.setText("");
                    Intent intent = new Intent(this, AllTasks.class);
                    intent.putExtra("token", encodedToken);
                    startActivity(intent);
                }
            } catch (Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}