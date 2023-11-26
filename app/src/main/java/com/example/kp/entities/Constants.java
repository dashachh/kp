package com.example.kp.entities;

public class Constants {
//    public static final String token = "Basic ZGFyeWEuY2h1a2huaW5hOmlsaXNhMTNEYQ==";
    public static final String baseURL = "https://jira.glowbyteconsulting.com";
    public static final String getAllIssuesURl = "/rest/api/2/search?jql=assignee%20%3D%20currentUser()%20AND%20resolution%20%3D%20Unresolved%20order%20by%20updated";
    public static final String getCurrentUserURL = "/rest/api/2/myself";

}
