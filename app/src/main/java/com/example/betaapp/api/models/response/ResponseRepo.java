package com.example.betaapp.api.models.response;

import com.google.gson.annotations.SerializedName;

public class ResponseRepo {

    @SerializedName("id")
    private long gitId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public long getGitId() {
        return gitId;
    }

    public void setGitId(long gitId) {
        this.gitId = gitId;
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

    @Override
    public String toString() {
        return "RepoDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
