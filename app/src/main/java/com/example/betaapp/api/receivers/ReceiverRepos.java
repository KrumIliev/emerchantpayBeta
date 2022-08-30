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

    private static final String ACTION_STARRED_REPOS_LOADED = "com.example.betaapp.api.receivers.ACTION_STARRED_REPOS_LOADED";

    private static final String ACTION_STARRED_REPOS_LOAD_FAILED = "com.example.betaapp.api.receivers.ACTION_STARRED_REPOS_LOAD_FAILED";

    private static final String EXTRA_REPOS_DATA = "com.example.betaapp.api.receivers.EXTRA_REPOS_DATA";

    private static final String EXTRA_USER_NAME = "com.example.betaapp.api.receivers.EXTRA_USER_NAME";

    private final OnRepositoryListLoadingCompleted reposCompleteListener;

    private final OnStarredRepositoryListLoadingCompleted starredCompleteListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public ReceiverRepos(OnRepositoryListLoadingCompleted reposCompleteListener, OnStarredRepositoryListLoadingCompleted starredCompleteListener) {
        super();
        this.reposCompleteListener = reposCompleteListener;
        this.starredCompleteListener = starredCompleteListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void addActions(IntentFilter filter) {
        filter.addAction(ACTION_REPOS_LOADED);
        filter.addAction(ACTION_REPOS_LOAD_FAILED);
        filter.addAction(ACTION_STARRED_REPOS_LOADED);
        filter.addAction(ACTION_STARRED_REPOS_LOAD_FAILED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        switch (action) {
            case ACTION_REPOS_LOADED:
                reposCompleteListener.onRepoListLoadingCompleted(
                        data.getStringExtra(EXTRA_USER_NAME));
                break;

            case ACTION_STARRED_REPOS_LOADED:
                starredCompleteListener.onStarredRepoListLoadingCompleted(data.getStringExtra(EXTRA_USER_NAME));
                break;
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastReposLoaded(String userName, boolean starred) {
        Intent intent = new Intent(starred ? ACTION_STARRED_REPOS_LOADED : ACTION_REPOS_LOADED);
        intent.putExtra(EXTRA_USER_NAME, userName);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    public static void broadcastReposFailed(String userName, boolean starred) {
        Intent intent = new Intent(starred ? ACTION_STARRED_REPOS_LOAD_FAILED : ACTION_REPOS_LOAD_FAILED);
        intent.putExtra(EXTRA_USER_NAME, userName);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnRepositoryListLoadingCompleted {
        void onRepoListLoadingCompleted(String userName);
    }

    public interface OnStarredRepositoryListLoadingCompleted {
        void onStarredRepoListLoadingCompleted(String userName);
    }
}
