package com.example.betaapp.db.models;

import com.example.betaapp.api.models.response.ResponseUser;

public class DBOUser extends DBOBase {

    private String userName;

    private String displayName;

    private String avatarUrl;

    private int followers;

    private int following;

    public DBOUser() {}

    public DBOUser(ResponseUser user) {
        setUserName(user.getUserName());
        setDisplayName(user.getDisplayName());
        setAvatarUrl(user.getAvatarUrl());
        setFollowers(user.getFollowers());
        setFollowing(user.getFollowing());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "DBOUser{" +
                "userName='" + userName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
