package com.test.callmanager.models;

public class SessionInfo {

    String name;
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SessionInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
