package com.example.kp.activities;

import static com.example.kp.entities.Constants.getIssueInfoURl;
import static com.example.kp.entities.Constants.putEditIssueURl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.entities.Issue;
import com.example.kp.entities.Requests;

import org.json.JSONException;
import org.json.JSONObject;

public class IssueInfo extends AppCompatActivity {

    // TODO: 27.12.2023 кнопочку "назад"
    TextView issueIdView;
    TextView issueNameView;
    TextView issueDescriptionView;
    TextView issueStatusView;
    String encodedToken;
    AlertDialog dialog;
    EditText editDescription;

    private static final Issue issueInfo = new Issue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_info);
        Intent intent = getIntent();
        encodedToken = intent.getSerializableExtra("token").toString();
        String issueId = intent.getSerializableExtra("issue_id").toString();
        issueIdView = findViewById(R.id.issueId);
        issueNameView = findViewById(R.id.issueName);
        issueDescriptionView = findViewById(R.id.issueDescription);
        issueStatusView = findViewById(R.id.issueStatus);

        String allTasksJSON = "";
        Requests.GetRequestAsync allTasksRequest = new Requests.GetRequestAsync();
        try {
            allTasksJSON = allTasksRequest.execute(encodedToken, getIssueInfoURl + issueId).get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            JSONObject issueInfoJSON = new JSONObject(allTasksJSON);
            issueInfo.setId(issueInfoJSON.get("key").toString());
            JSONObject issueFields = issueInfoJSON.getJSONObject("fields");
            issueInfo.setName(issueFields.get("summary").toString());
            issueInfo.setDescription(issueFields.get("description").toString());
            issueInfo.setStatus(issueFields.getJSONObject("status").get("name").toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        issueIdView.setText(issueInfo.getId());
        issueNameView.setText(issueInfo.getName());
        issueDescriptionView.setText(issueInfo.getDescription());
        issueStatusView.setText(issueInfo.getStatus());
    }

    public void logTime(View view) {
    }

    public void editDescription(View view) {
        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Edit description of issue");
        editDescription = new EditText(this);
        editDescription.setText(issueDescriptionView.getText());

        dialog.setView(editDescription);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.cancel());
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE",
                (dialog, which) -> {
                    if (!editDescription.getText().toString().equals(issueDescriptionView.getText().toString())) {
                        issueDescriptionView.setText(editDescription.getText());
                    }
//                    Toast toast = Toast.makeText(getApplicationContext(), "Введите описание", Toast.LENGTH_SHORT);
//                    toast.show();
                }
        );
        dialog.show();

//        Requests.PutRequestAsync editRequest = new Requests.PutRequestAsync();
        String jsonData = "{\"fields\" : {\"description\": " + "\"" + issueInfo.getDescription() + "\"" + "}}";
//        editRequest.execute(encodedToken, putEditIssueURl + issueInfo.getId(), jsonData);
    }
}