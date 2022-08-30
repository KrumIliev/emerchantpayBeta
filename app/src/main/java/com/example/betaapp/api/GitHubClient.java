package com.example.betaapp.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.betaapp.BaseApplication;
import com.example.betaapp.api.actions.GitHubRequest;
import com.example.betaapp.api.models.request.RequestDTO;

public class GitHubClient extends HurlStack {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GitHubClient.class.getSimpleName();

    private static GitHubClient instance;

    private final RequestQueue requestQueue;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GitHubClient() {
        requestQueue = Volley.newRequestQueue(BaseApplication.getContext(), this);
    }

    public static GitHubClient getInstance() {
        if (instance == null) {
            instance = new GitHubClient();
        }
        return instance;
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    /**
     * Send request via Volley
     *
     * @param request of any type POST, GET ...
     */
    public void send(GitHubRequest request) {
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    /**
     * Cancels all pending requests in the Volley request queue
     */
    public void cancelAllRequests() {
        Log.d(LOG_TAG, "Canceling all pending requests");
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
