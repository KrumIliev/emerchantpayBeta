package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.utils.BaseApplication;

public class ReceiverRepos extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverRepos.class.getSimpleName();

    private static final String ACTION_REPOS_LOADED = "com.example.betaapp.api.receivers.ACTION_REPOS_LOADED";

    private static final String ACTION_STARRED_REPOS_LOADED = "com.example.betaapp.api.receivers.ACTION_STARRED_REPOS_LOADED";

    private static final String EXTRA_REPOS_DATA = "com.example.betaapp.api.receivers.EXTRA_REPOS_DATA";

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
        filter.addAction(ACTION_STARRED_REPOS_LOADED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        switch (action) {
            case ACTION_REPOS_LOADED:
                reposCompleteListener.onRepoListLoadingCompleted();
                break;

            case ACTION_STARRED_REPOS_LOADED:
                starredCompleteListener.onStarredRepoListLoadingCompleted();
                break;
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastReposLoadComplete() {
        broadcastReposResult(ACTION_REPOS_LOADED);
    }

    public static void broadcastStarredReposLoadComplete() {
        broadcastReposResult(ACTION_STARRED_REPOS_LOADED);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private static void broadcastReposResult(String action) {
        BaseApplication.getContext().sendBroadcast(new Intent(action));
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnRepositoryListLoadingCompleted {
        void onRepoListLoadingCompleted();
    }

    public interface OnStarredRepositoryListLoadingCompleted {
        void onStarredRepoListLoadingCompleted();
    }
}
