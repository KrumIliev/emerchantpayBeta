package com.example.betaapp.api.actions;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseRepo;

import java.lang.reflect.Type;

import com.example.betaapp.api.receivers.ReceiverRepos;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBORepo;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GetReposList extends GitHubRequest<RequestDTO> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetReposList.class.getSimpleName();

    public static final String EXTRA_USER_NAME = "com.example.betaapp.api.actions.EXTRA_USER_NAME";

    private final String userName;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetReposList(Intent intent) {
        super(Method.GET, getUrl(intent), true);
        userName = intent.getStringExtra(EXTRA_USER_NAME);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected RequestDTO getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(String response) {
        Type listType = new TypeToken<List<ResponseRepo>>() {
        }.getType();
        List<ResponseRepo> repos = gson.fromJson(response, listType);
        long userId = DAOUsers.getUserIdByName(userName);
        ArrayList<DBORepo> dboRepos = new ArrayList<>();
        for (ResponseRepo repo : repos) {
            DBORepo dbo = new DBORepo(repo);
            dbo.setUserId(userId);
            dbo.setStarred(false);
            DAORepos.insertRepo(dbo);
            dboRepos.add(dbo);
        }

        ReceiverRepos.broadcastReposLoaded(dboRepos);
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        ReceiverRepos.broadcastReposFailed(userName);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * @return Formatted URL
     * <p> -> https://api.github.com/users/USERNAME/repos
     * <p> -> https://api.github.com/user/repos
     */
    private static String getUrl(Intent intent) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API_URL);

        String userName = intent.getStringExtra(EXTRA_USER_NAME);
        if (TextUtils.isEmpty(userName)) {
            builder.append("/user/repos");

        } else {
            builder.append("/users/");
            builder.append(userName);
            builder.append("/repos");
        }

        return builder.toString();
    }
}
