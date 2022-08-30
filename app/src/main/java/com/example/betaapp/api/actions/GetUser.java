package com.example.betaapp.api.actions;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.response.ResponseUser;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBOUser;

public class GetUser extends GitHubRequest<Void> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetUser.class.getSimpleName();

    public static final String EXTRA_USER_NAME = "com.example.betaapp.api.actions.EXTRA_USER_NAME";

    private final String userName;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetUser(Intent intent) {
        super(Method.GET, getURL(intent), true);
        this.userName = intent.getStringExtra(EXTRA_USER_NAME);
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
        ResponseUser respUser = gson.fromJson(response, ResponseUser.class);
        DBOUser user = new DBOUser(respUser);
        // When retrieving logged user we don't use user name
        user.setLogged(TextUtils.isEmpty(userName));
        DAOUsers.insertUser(user);
        ReceiverUser.broadcastUserLoaded(user);
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        ReceiverUser.broadcastUserLoadingFailed(userName);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * @return Formatted URL
     * <p> -> https://api.github.com/users/USERNAME
     * <p> -> https://api.github.com/user
     */
    private static String getURL(Intent intent) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API_URL);

        String user = intent.getStringExtra(EXTRA_USER_NAME);
        if (TextUtils.isEmpty(user)) {
            builder.append("/user");
        } else {
            builder.append("/users/");
            builder.append(user);
        }

        return builder.toString();
    }
}
