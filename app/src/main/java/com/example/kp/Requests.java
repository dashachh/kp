package com.example.kp;

import static com.example.kp.Constants.baseURL;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class Requests {
    public static class GetRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... extraURL) {
            StringBuilder allMyIssues = new StringBuilder();
            try {
                URL url = new URL(baseURL + Arrays.stream(extraURL).findFirst().get());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type", "apllication/json");
                connection.setRequestProperty("Authorization", "Basic ZGFyeWEuY2h1a2huaW5hOmlsaXNhMTNEYQ==");
                if (connection.getResponseCode() == 200) {
                    String output = "";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((output = bufferedReader.readLine()) != null) {
                        allMyIssues.append(output);
                    }
                    connection.disconnect();
                    return allMyIssues.toString();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }
}
