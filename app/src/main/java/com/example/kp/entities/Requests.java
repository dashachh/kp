package com.example.kp.entities;

import static com.example.kp.entities.Constants.baseURL;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Requests {
    public static class GetRequestAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(baseURL + params[1]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type", "apllication/json");
                connection.setRequestProperty("Authorization", params[0]);
                if (connection.getResponseCode() == 200) {
                    String output = "";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((output = bufferedReader.readLine()) != null) {
                        response.append(output);
                    }
                    connection.disconnect();
                    return response.toString();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }
}
