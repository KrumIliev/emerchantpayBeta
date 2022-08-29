package com.example.betaapp.api.actions;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseUser;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBOUser;

public class GetLoggedUser extends GitHubRequest<RequestDTO, ResponseUser> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetLoggedUser.class.getSimpleName();

    private static final String URL = BASE_API_URL + "/user";

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetLoggedUser() {
        super(Method.GET, URL, ResponseUser.class, true);
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
        user.setLogged(true);
        DAOUsers.insertUser(user);
        ReceiverUser.broadcastUserLoaded(user);
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        // When retrieving logged user we don't use user name
        ReceiverUser.broadcastUserLoadingFailed(null);
    }
}
