package com.example.kp.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    //    public static final String token = "Basic ZGFyeWEuY2h1a2huaW5hOmlsaXNhMTNEYQ==";
    public static final List<Integer> OK_RESPONSE_CODE = Collections.unmodifiableList(Arrays.asList(200, 201, 204));

    public static final String baseURL = "https://jira.glowbyteconsulting.com";
    public static final String getAllIssuesURl = "/rest/api/2/search?jql=assignee%20%3D%20currentUser()%20AND%20resolution%20%3D%20Unresolved%20order%20by%20updated";
    public static final String getIssueInfoURl = "/rest/api/2/issue/";

    public String putEditIssueURl(String issueId) {
        return "/rest/api/2/issue/" + issueId;
    }
    public static final String getCurrentUserURL = "/rest/api/2/myself";

    public String getIssueWorklogURL(String issueId) {
        return "/rest/api/2/issue/" + issueId + "/worklog";
    }

    public String postIssueWorklogURL(String issueId) {
        return "/rest/api/2/issue/" + issueId + "/worklog";
    }
}
