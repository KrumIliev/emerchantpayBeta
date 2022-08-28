package com.example.betaapp.api.actions;

import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.utils.Cache;
import com.example.betaapp.api.models.request.RequestOAuthToken;
import com.example.betaapp.api.models.response.ResponseOAuthToken;
import com.example.betaapp.api.receivers.ReceiverAuthentication;

public class GetOAuthToken extends GitHubRequest<RequestOAuthToken, ResponseOAuthToken> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetOAuthToken.class.getSimpleName();

    private static final String URL = BASE_HTTP_URL + "/login/oauth/access_token";

    public static final String EXTRA_CODE = "EXTRA_CODE";

    private final String code;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetOAuthToken(Intent intent) {
        super(Method.POST, URL, ResponseOAuthToken.class);
        this.code = intent.getStringExtra(EXTRA_CODE);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected RequestOAuthToken getRequest() {
        return new RequestOAuthToken(CLIENT_ID, CLIENT_SECRET, code);
    }

    @Override
    protected void onRequestSuccess(ResponseOAuthToken response) {
        Log.d(LOG_TAG, response.getAccessToken());
        Cache.gitHubToken = response.getAccessToken();
        ReceiverAuthentication.broadcastAuthenticationCompleted(true);
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        Cache.gitHubToken = null;
        ReceiverAuthentication.broadcastAuthenticationCompleted(false);
    }
}
