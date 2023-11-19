package com.example.kp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AllTasks extends AppCompatActivity {

    private static final String baseURL = "https://jira.glowbyteconsulting.com";
    private static final String allIssuesURl = "/rest/api/2/search?jql=assignee%20%3D%20currentUser()%20AND%20resolution%20%3D%20Unresolved%20order%20by%20updated";
    TextView issuesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        issuesText = findViewById(R.id.textIssues);
    }

    // TODO: 19.11.2023 сделать отдельный класс для запросиков, перенести это туда с аргументом url
    class IssuesExec extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String allMyIssues = "";
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
                    return allMyIssues;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }

    public void allIssues(View view) {
        String issues = "";
        IssuesExec task = new IssuesExec();
        try {
            issues = task.execute().get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
//        new IssuesExec().execute();
//        JSONObject issues = new JSONObject(allMyIssues);
//        String issueKey = issues.getJSONObject("issues").getJSONArray("key").get(0).toString();
        issuesText.setText(issues);
    }
}