package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.BaseApplication;
import com.example.betaapp.db.models.DBORepo;

import java.util.ArrayList;

public class ReceiverRepos extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverRepos.class.getSimpleName();

    private static final String ACTION_REPOS_LOADED = "com.example.betaapp.api.receivers.ACTION_REPOS_LOADED";

    private static final String ACTION_REPOS_LOAD_FAILED = "com.example.betaapp.api.receivers.ACTION_REPOS_LOAD_FAILED";

    private static final String EXTRA_REPOS_DATA = "com.example.betaapp.api.receivers.EXTRA_REPOS_DATA";

    private static final String EXTRA_USER_NAME = "com.example.betaapp.api.receivers.EXTRA_USER_NAME";

    private final OnRepositoryListLoadingCompleted completeListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public ReceiverRepos(OnRepositoryListLoadingCompleted completeListener) {
        super();
        this.completeListener = completeListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void addActions(IntentFilter filter) {
        filter.addAction(ACTION_REPOS_LOADED);
        filter.addAction(ACTION_REPOS_LOAD_FAILED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        switch (action) {
            case ACTION_REPOS_LOADED:
                completeListener.onRepoListLoadingCompleted((ArrayList<DBORepo>) data.getSerializableExtra(EXTRA_REPOS_DATA));
                break;

            case ACTION_REPOS_LOAD_FAILED:
                completeListener.onRepoListLoadingFailed(data.getStringExtra(EXTRA_USER_NAME));
                break;
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastReposLoaded(ArrayList<DBORepo> repos) {
        Intent intent = new Intent(ACTION_REPOS_LOADED);
        intent.putExtra(EXTRA_REPOS_DATA, repos);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    public static void broadcastReposFailed(String userName) {
        Intent intent = new Intent(ACTION_REPOS_LOAD_FAILED);
        intent.putExtra(EXTRA_USER_NAME, userName);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnRepositoryListLoadingCompleted {
        void onRepoListLoadingCompleted(ArrayList<DBORepo> repos);

        void onRepoListLoadingFailed(String userName);
    }
}
