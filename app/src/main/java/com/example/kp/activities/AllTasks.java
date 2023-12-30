package com.example.kp.activities;

import static com.example.kp.entities.Constants.getAllIssuesURl;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kp.R;
import com.example.kp.entities.Issue;
import com.example.kp.entities.IssueAdapter;
import com.example.kp.entities.Requests;
import com.example.kp.entities.SelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity implements SelectListener {

    RecyclerView issuesView;
    IssueAdapter adapter;
    String encodedToken;
    AlertDialog dialog;
    private static final List<Issue> allIssues = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        allIssues.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_issues);
        Intent intent = getIntent();
        encodedToken = intent.getSerializableExtra("token").toString();
        issuesView = findViewById(R.id.allIssues);
        issuesView.addItemDecoration(
                new DividerItemDecoration(
                        issuesView.getContext(), DividerItemDecoration.VERTICAL));
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
                Issue issue = new Issue();
                issue.setName(task.getJSONObject("fields").get("summary").toString());
                issue.setId(task.get("key").toString());
                allIssues.add(issue);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        adapter = new IssueAdapter(this, allIssues, this);
        issuesView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Issue issue) {
        Intent intent = new Intent(this, IssueInfo.class);
        intent.putExtra("token", encodedToken);
        intent.putExtra("issue_id", issue.getId());
        startActivity(intent);
    }

    public void signOut(View view) {
        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Are you sure you want to sign out?");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.cancel());
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SIGN OUT",
                (dialog, which) -> finish());
        dialog.show();
    }

}