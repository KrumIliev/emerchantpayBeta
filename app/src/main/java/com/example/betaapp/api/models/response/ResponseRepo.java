package com.example.betaapp.api.models.response;

import com.google.gson.annotations.SerializedName;

public class ResponseRepo {

    @SerializedName("id")
    private long gitId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private Owner owner;

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "RepoDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Owner {

        @SerializedName("login")
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
