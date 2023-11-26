package com.example.kp.activities;

import static com.example.kp.entities.Constants.getAllIssuesURl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.entities.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.stream.Collectors;

public class AllTasks extends AppCompatActivity {

    TextView issuesText;
    private static final HashMap<String, String> tasks = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        Intent intent = getIntent();
        String encodedToken = intent.getSerializableExtra("token").toString();
        issuesText = findViewById(R.id.textIssues);
        String allTasksJSON = "";
        Requests.GetRequestAsync allTasksRequest = new Requests.GetRequestAsync();
        try {
            allTasksJSON = allTasksRequest.execute(encodedToken, getAllIssuesURl).get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            JSONArray issuesJSON = new JSONObject(allTasksJSON).getJSONArray("issues");
            for (int i = 0, size = issuesJSON.length(); i < size; i++) {
                JSONObject task = new JSONObject(issuesJSON.getJSONObject(i).toString());
                tasks.put(
                        task.get("key").toString(),
                        task.getJSONObject("fields").get("summary").toString()
                );
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String tasksString = tasks.keySet().stream().map(key -> key + " = " + tasks.get(key)).collect(Collectors.joining("\n"));
        issuesText.setText(tasksString);
        // TODO: 25.11.2023 создание кнопок или кликабельных ссылок-названий тасков
    }
}