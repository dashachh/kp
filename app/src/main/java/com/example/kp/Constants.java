package com.example.kp;

public class Constants {
    public static final String baseURL = "https://jira.glowbyteconsulting.com";
    public static final String allIssuesURl = "/rest/api/2/search?jql=assignee%20%3D%20currentUser()%20AND%20resolution%20%3D%20Unresolved%20order%20by%20updated";

}
