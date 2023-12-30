package com.example.kp.entities;

import static com.example.kp.entities.Constants.OK_RESPONSE_CODE;
import static com.example.kp.entities.Constants.baseURL;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

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
                if (OK_RESPONSE_CODE.contains(connection.getResponseCode())) {
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

    public static class PutRequestAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(baseURL + params[1]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", params[0]);

                byte[] outputBytes = params[2].getBytes("UTF-8");

                connection.setDoOutput(true);
                OutputStream out = connection.getOutputStream();
                out.write(outputBytes);

                if (OK_RESPONSE_CODE.contains(connection.getResponseCode())) {
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

    public static class PostRequestAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(baseURL + params[1]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("Authorization", params[0]);

                byte[] outputBytes = params[2].getBytes("UTF-8");

                connection.setDoOutput(true);
                OutputStream out = connection.getOutputStream();
                out.write(outputBytes);

                if (OK_RESPONSE_CODE.contains(connection.getResponseCode())) {
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
