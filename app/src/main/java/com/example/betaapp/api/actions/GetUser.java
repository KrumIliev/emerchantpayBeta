package com.example.betaapp.api.actions;

import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseDTO;
import com.example.betaapp.api.models.response.ResponseUser;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBOUser;

public class GetUser extends GitHubRequest<RequestDTO, ResponseUser> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetUser.class.getSimpleName();

    private static final String URL = BASE_API_URL + "/users/";

    public static final String EXTRA_USER_NAME = "com.example.betaapp.api.actions.EXTRA_USER_NAME";

    private final String userName;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetUser(Intent intent) {
        super(Method.GET, URL + intent.getStringExtra(EXTRA_USER_NAME), ResponseUser.class, true);
        this.userName = intent.getStringExtra(EXTRA_USER_NAME);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected RequestDTO getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(ResponseUser response) {
        Log.d(LOG_TAG, response.toString());
        DBOUser user = new DBOUser(response);
        DAOUsers.insertUser(user);
        ReceiverUser.broadcastUserLoaded(user);
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        ReceiverUser.broadcastUserLoadingFailed(userName);
    }
}
