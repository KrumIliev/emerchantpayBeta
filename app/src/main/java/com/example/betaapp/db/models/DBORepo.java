package com.example.betaapp.db.models;

import com.example.betaapp.api.models.response.ResponseRepo;

import java.io.Serializable;

public class DBORepo extends DBOBase implements Serializable {

    private String name;

    private String description;

    private boolean isStarred;

    private long userId;

    public DBORepo() {
    }

    public DBORepo(ResponseRepo responseRepo) {
        this.name = responseRepo.getName();
        this.description = responseRepo.getDescription();
    }

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

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
