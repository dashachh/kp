package com.example.kp.activities;

import static com.example.kp.entities.Constants.getIssueInfoURl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kp.R;
import com.example.kp.entities.Constants;
import com.example.kp.entities.Issue;
import com.example.kp.entities.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class IssueInfo extends AppCompatActivity {
    Constants constants = new Constants();

    TextView issueIdView;
    TextView issueNameView;
    TextView issueDescriptionView;
    TextView issueStatusView;
    TextView issueLoggedView;
    TextView issueRemainingView;
    String encodedToken;
    AlertDialog dialog;
    EditText editDescription;
    EditText editLogTime;
    EditText editLogComment;

    private static final Issue issueInfo = new Issue();
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ");

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
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
        issueLoggedView = findViewById(R.id.issueLogged);
        issueRemainingView = findViewById(R.id.issueRemaining);

        getIssueInfo(issueId);

        issueIdView.setText(issueInfo.getId());
        issueNameView.setText(issueInfo.getName());
        issueDescriptionView.setText(issueInfo.getDescription());
        issueStatusView.setText(issueInfo.getStatus());
        issueLoggedView.setText(issueInfo.getLogged());
        issueRemainingView.setText(issueInfo.getRemaining());
    }

    private void getIssueInfo(String issueId) {
        String issueInfoString = "";
        Requests.GetRequestAsync issueInfoRequest = new Requests.GetRequestAsync();
        try {
            issueInfoString = issueInfoRequest.execute(encodedToken, getIssueInfoURl + issueId).get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            JSONObject issueInfoJSON = new JSONObject(issueInfoString);
            issueInfo.setId(issueInfoJSON.get("key").toString());
            JSONObject issueFields = issueInfoJSON.getJSONObject("fields");
            issueInfo.setName(issueFields.get("summary").toString());
            issueInfo.setDescription(issueFields.get("description").toString());
            issueInfo.setStatus(issueFields.getJSONObject("status").get("name").toString());
            try {
                JSONObject timeTracking = issueFields.getJSONObject("timetracking");
                int loggedHours = Integer.parseInt(timeTracking.get("timeSpentSeconds").toString()) / 3600;
                int remainingHours = Integer.parseInt(timeTracking.get("remainingEstimateSeconds").toString()) / 3600;
                issueInfo.setLogged(loggedHours + "h");
                issueInfo.setRemaining(remainingHours + "h");
            } catch (JSONException e) {
                issueInfo.setLogged("0h");
                issueInfo.setRemaining("0h");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("SetTextI18n")
    public void logTime(View view) {

        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Add a worklog to an issue.");
        Context context = view.getContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        editLogTime = new EditText(context);
        editLogTime.setHint("Time");
        layout.addView(editLogTime);

        editLogComment = new EditText(context);
        editLogComment.setHint("Comment");
        layout.addView(editLogComment);

        dialog.setView(layout);

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.cancel());
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "LOG",
                (dialog, which) -> {
                    if (!editLogTime.getText().toString().isEmpty() && !editLogComment.getText().toString().isEmpty()) {
                        Requests.PostRequestAsync logRequest = new Requests.PostRequestAsync();
//                        String jsonData = "{\"fields\" : {\"description\": " + "\"" + editDescription.getText().toString() + "\"" + "}}";
                        String jsonData = "{\"comment\" : \"" + editLogComment.getText().toString() + "\", \"started\" : \"" + sdf2.format(new Timestamp(System.currentTimeMillis())).replace("Z", "") + "\", \"timeSpentSeconds\" : " + Integer.parseInt(editLogTime.getText().toString()) * 3600 + "}";
                        try {
                            if (!logRequest.execute(encodedToken, constants.postIssueWorklogURL(issueInfo.getId()), jsonData).get().equals(null)) {
                                getIssueInfo(issueInfo.getId());
                                issueLoggedView.setText(issueInfo.getLogged());
                                issueRemainingView.setText(issueInfo.getRemaining());
                            }
                        } catch (Exception exception) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Нет доступа к логированию!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Заполните все необходимые поля!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
        );
        dialog.show();
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
                        Requests.PutRequestAsync editRequest = new Requests.PutRequestAsync();
                        String jsonData = "{\"fields\" : {\"description\": " + "\"" + editDescription.getText().toString() + "\"" + "}}";
                        try {
                            if (!editRequest.execute(encodedToken, constants.putEditIssueURl(issueInfo.getId()), jsonData).get().equals(null)) {
                                getIssueInfo(issueInfo.getId());
                                issueDescriptionView.setText(issueInfo.getDescription());
                            }
                        } catch (Exception exception) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Нет доступа к редактированию описания задачи!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
        );
        dialog.show();
    }

    public void back(View view) {
        finish();
    }

    public void signOut(View view) {
        dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Are you sure you want to sign out?");

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                (dialog, which) -> dialog.cancel());
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SIGN OUT",
                (dialog, which) -> {
                    Intent intent = new Intent(this, Authorization.class);
                    startActivity(intent);
                });
        dialog.show();
    }
}