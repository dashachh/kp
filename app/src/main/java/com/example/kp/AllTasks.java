package com.example.kp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AllTasks extends AppCompatActivity {

    private static final String baseURL = "https://jira.glowbyteconsulting.com";
    private static final String allIssuesURl = "/rest/api/2/search?jql=assignee%20%3D%20currentUser()%20AND%20resolution%20%3D%20Unresolved%20order%20by%20updated";
    private static String allMyIssues = "";
    TextView issuesText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        issuesText = findViewById(R.id.textIssues);
    }

    class issuesExec extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(baseURL + allIssuesURl);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type", "apllication/json");
                connection.setRequestProperty("Authorization", "Basic ZGFyeWEuY2h1a2huaW5hOmlsaXNhMTNEYQ==");
                if (connection.getResponseCode() == 200) {
                    String output = "";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((output = bufferedReader.readLine()) != null) {
                        allMyIssues += output;
                    }
                    connection.disconnect();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }

    public void allIssues(View view) throws JSONException {
        new issuesExec().execute();

        JSONObject issues = new JSONObject(allMyIssues);
        String issueKey = issues.getJSONObject("issues").getJSONArray("key").get(0).toString();
        issuesText.setText(issueKey);
    }
}