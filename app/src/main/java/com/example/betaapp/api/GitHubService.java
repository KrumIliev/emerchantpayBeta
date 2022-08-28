package com.example.betaapp.api;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.betaapp.BaseApplication;
import com.example.betaapp.api.actions.GetOAuthToken;
import com.example.betaapp.api.actions.GetLoggedUser;

public class GitHubService extends IntentService {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GitHubService.class.getSimpleName();

    private static final String ACTION_BASE = "com.example.betaapp.api.";
    private static final String ACTION_GET_TOKEN = ACTION_BASE + "ACTION_GET_TOKEN";
    private static final String ACTION_GET_LOGGED_USER = ACTION_BASE + "ACTION_GET_LOGGED_USER";
    private static final String ACTION_STOP = ACTION_BASE + "ACTION_STOP";

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GitHubService() {
        super(LOG_TAG);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();

            if (TextUtils.isEmpty(action)) {
                Log.w(LOG_TAG, "onHandleIntent with empty or null action");
                return;
            }

            handleAction(action, intent);

        } else {
            Log.w(LOG_TAG, "onHandleIntent intent null");
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    /**
     * Sends POST request to GitHub HTTP to generate OAuth token
     *
     * @param code Retrieved via user login into browser window
     */
    public static void getToken(String code) {
        Intent intent = new Intent(BaseApplication.getContext(), GitHubService.class);
        intent.setAction(ACTION_GET_TOKEN);
        intent.putExtra(GetOAuthToken.EXTRA_CODE, code);
        BaseApplication.getContext().startService(intent);
    }

    /**
     * Sends GET request to GitHub API to retrieve logged in user information
     */
    public static void getLoggedUser () {
        Intent intent = new Intent(BaseApplication.getContext(), GitHubService.class);
        intent.setAction(ACTION_GET_LOGGED_USER);
        BaseApplication.getContext().startService(intent);
    }

    /**
     * Cancel all pending requests
     */
    public static void stopService() {
        Intent intent = new Intent(BaseApplication.getContext(), GitHubService.class);
        intent.setAction(ACTION_STOP);
        BaseApplication.getContext().startService(intent);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * Handler for all service actions.
     */
    private void handleAction (String action, Intent intent) {
        Log.d(LOG_TAG, "Action: " + action);
        switch (action) {
            case ACTION_GET_TOKEN:
                GitHubClient.getInstance().send(new GetOAuthToken(intent));
                break;

            case ACTION_GET_LOGGED_USER:
                GitHubClient.getInstance().send(new GetLoggedUser());
                break;

            case ACTION_STOP:
                GitHubClient.getInstance().cancelAllRequests();
                break;

            default:
                Log.w(LOG_TAG, "onHandleIntent with unknown action");
        }
    }
}
