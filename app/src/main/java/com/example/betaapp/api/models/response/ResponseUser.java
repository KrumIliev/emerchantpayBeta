package com.example.betaapp.api.models.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ResponseUser {

    @SerializedName("login")
    private String userName;

    @SerializedName("name")
    private String displayName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("followers")
    private int followers;

    @SerializedName("following")
    private int following;

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseUser{" +
                "userName='" + userName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
