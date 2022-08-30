package com.example.betaapp.activities.user;

import android.content.Context;
import android.text.TextUtils;

import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverRepos;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.utils.ConnectivityMonitor;

import java.util.ArrayList;

public class UserModel implements UserInterfaces.Model, ReceiverUser.OnUserLoadingCompleted, ReceiverRepos.OnRepositoryListLoadingCompleted {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final OnUserLoadingFinishListener onUserLoadingFinishListener;

    private final OnReposLoadingFinishListener onReposLoadingFinishListener;

    private final ReceiverUser receiverUser;

    private final ReceiverRepos receiverRepos;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserModel(OnUserLoadingFinishListener onUserLoadingFinishListener, OnReposLoadingFinishListener onReposLoadingFinishListener) {
        this.onUserLoadingFinishListener = onUserLoadingFinishListener;
        this.onReposLoadingFinishListener = onReposLoadingFinishListener;
        receiverUser = new ReceiverUser(this);
        receiverRepos = new ReceiverRepos(this);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void register(Context context) {
        receiverUser.register(context);
        receiverRepos.register(context);
    }

    @Override
    public void unregister(Context context) {
        receiverUser.unregister(context);
        receiverRepos.unregister(context);
    }

    @Override
    public void getUserData(String userName) {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {
            GitHubService.getUser(userName);

        } else {
            // If we don't have internet connection try fetching the user from the local storage
            getUserFromLocalStorage(userName);
        }
    }

    @Override
    public void onUserLoaded(DBOUser user) {
        onUserLoadingFinishListener.onUserLoadingCompleted(user);
    }

    @Override
    public void onUserLoadingFailed(String userName) {
        // Try fetching the user from the local storage
        getUserFromLocalStorage(userName);
    }

    @Override
    public void getUserRepos(String userName) {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {
            GitHubService.getRepos(userName);

        } else {
            // If we don't have internet connection try fetching the user from the local storage
            getReposFromLocalStorage(userName);
        }
    }

    @Override
    public void onRepoListLoadingCompleted(ArrayList<DBORepo> repos) {
        onReposLoadingFinishListener.onReposLoadingCompleted(repos);
    }

    @Override
    public void onRepoListLoadingFailed(String userName) {
        // Try fetching the repos from the local storage
        getReposFromLocalStorage(userName);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private void getUserFromLocalStorage(String userName) {
        DBOUser user;
        if (TextUtils.isEmpty(userName)) {
            // Get the logged in user
            user = DAOUsers.getLoggedUser();
        } else {
            // Get the user by user name
            user = DAOUsers.getUserByName(userName);
        }

        if (user != null) {
            onUserLoadingFinishListener.onUserLoadingCompleted(user);
        } else {
            onUserLoadingFinishListener.onUserLoadingFailed();
        }
    }

    private void getReposFromLocalStorage(String userName) {
        long userId = DAOUsers.getUserIdByName(userName);
        if (userId != -1) {
            ArrayList<DBORepo> repos = DAORepos.getReposByUserId(userId);
            if (!repos.isEmpty()) {
                onReposLoadingFinishListener.onReposLoadingCompleted(repos);
            } else {
                onReposLoadingFinishListener.onReposLoadingFailed();
            }
        } else {
            onReposLoadingFinishListener.onReposLoadingFailed();
        }
    }
}
