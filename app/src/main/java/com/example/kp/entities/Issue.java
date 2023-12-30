package com.example.kp.entities;

public class Issue {

    private String id;
    private String name;
    private String description;
    private String status;
    private String logged;
    private String remaining;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogged() {
        return this.logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public String getRemaining() {
        return this.remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

}
