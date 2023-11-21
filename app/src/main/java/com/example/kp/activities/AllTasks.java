package com.example.kp.activities;

import static com.example.kp.Constants.allIssuesURl;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.Requests;

public class AllTasks extends AppCompatActivity {

    TextView issuesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        issuesText = findViewById(R.id.textIssues);
    }

    public void allTasks(View view) {
        String allTasks = "";
        Requests.GetRequest allTasksRequest = new Requests.GetRequest();
        try {
            allTasks = allTasksRequest.execute(allIssuesURl).get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
//        new IssuesExec().execute();
//        JSONObject issues = new JSONObject(allMyIssues);
//        String issueKey = issues.getJSONObject("issues").getJSONArray("key").get(0).toString();
        issuesText.setText(allTasks);
    }
}