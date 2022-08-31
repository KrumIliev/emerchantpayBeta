package com.example.betaapp.api.actions;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.example.betaapp.api.models.response.ResponseRepo;

import java.lang.reflect.Type;

import com.example.betaapp.api.receivers.ReceiverRepos;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBORepo;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetReposList extends GitHubRequest<Void> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetReposList.class.getSimpleName();

    public static final String EXTRA_USER_NAME = "com.example.betaapp.api.actions.EXTRA_USER_NAME";

    public static final String EXTRA_GET_STARRED = "com.example.betaapp.api.actions.EXTRA_GET_STARRED";

    private final String userName;

    private final boolean getStarredRepos;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetReposList(Intent intent) {
        super(Method.GET, getUrl(intent), true);
        userName = intent.getStringExtra(EXTRA_USER_NAME);
        getStarredRepos = intent.getBooleanExtra(EXTRA_GET_STARRED, false);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected Void getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(String response) {
        Type listType = new TypeToken<List<ResponseRepo>>() {
        }.getType();
        List<ResponseRepo> repos = gson.fromJson(response, listType);
        long userId = DAOUsers.getUserIdByName(userName);
        for (ResponseRepo repo : repos) {
            DBORepo dbo = new DBORepo(repo);
            dbo.setUserId(userId);
            dbo.setStarred(getStarredRepos);
            DAORepos.insertRepo(dbo);
        }

        sendResult();
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        sendResult();
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * @return Formatted URL
     * <p> -> https://api.github.com/user/repos
     * <p> -> https://api.github.com/users/USERNAME/repos
     * <p> -> https://api.github.com/user/starred
     * <p> -> https://api.github.com/users/USERNAME/starred
     */
    private static String getUrl(Intent intent) {
        String userName = intent.getStringExtra(EXTRA_USER_NAME);
        boolean getStarred = intent.getBooleanExtra(EXTRA_GET_STARRED, false);

        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API_URL);

        if (TextUtils.isEmpty(userName)) {
            if (getStarred) {
                builder.append("/user/starred");
            } else {
                builder.append("/user/repos");
            }

        } else {
            builder.append("/users/");
            builder.append(userName);
            if (getStarred) {
                builder.append("/starred");
            } else {
                builder.append("/repos");
            }
        }

        return builder.toString();
    }

    private void sendResult() {
        if (getStarredRepos) {
            ReceiverRepos.broadcastStarredReposLoadComplete();
        } else {
            ReceiverRepos.broadcastReposLoadComplete();
        }
    }
}
