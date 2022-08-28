package com.example.betaapp.api.actions;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseUser;

public class GetLoggedUser extends GitHubRequest<RequestDTO, ResponseUser> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetLoggedUser.class.getSimpleName();

    private static final String URL = BASE_API_URL + "/user";

    public GetLoggedUser() {
        super(Method.GET, URL, ResponseUser.class, true);
    }

    @Override
    protected RequestDTO getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(ResponseUser response) {
        Log.d(LOG_TAG, response.toString());
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
    }
}
