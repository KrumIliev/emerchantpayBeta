package com.example.betaapp.activities.user;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverRepos;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.utils.ConnectivityMonitor;

import java.util.ArrayList;

public class UserModel implements
        UserInterfaces.Model,
        ReceiverUser.OnUserLoadingCompleted,
        ReceiverRepos.OnRepositoryListLoadingCompleted,
        ReceiverRepos.OnStarredRepositoryListLoadingCompleted {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = UserModel.class.getSimpleName();

    private final OnUserDataLoadFinishListener onUserLoadingFinishListener;

    private final ReceiverUser receiverUser;

    private final ReceiverRepos receiverRepos;

    private final String userName;

    private DBOUser user;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserModel(String userName, OnUserDataLoadFinishListener onUserLoadingFinishListener) {
        this.userName = userName;
        this.onUserLoadingFinishListener = onUserLoadingFinishListener;
        receiverUser = new ReceiverUser(this);
        receiverRepos = new ReceiverRepos(this, this);
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
    public void getUserData() {
        user = null;
        getUser();
    }

    @Override
    public void onUserLoaded() {
        getUserFromLocalStorage();
    }

    @Override
    public void onRepoListLoadingCompleted() {
        getUserStarredRepos();
    }

    @Override
    public void onStarredRepoListLoadingCompleted() {
        getReposFromLocalStorage();
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private void getUser() {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {
            GitHubService.getUser(userName);

        } else {
            // If we don't have internet connection try fetching the user from the local storage
            getUserFromLocalStorage();
        }
    }

    private void getUserRepos() {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {
            // Get the user public and private repos
            GitHubService.getRepos(userName, false);

        } else {
            // If we don't have internet connection try fetching all user repos from the local storage
            getReposFromLocalStorage();
        }
    }

    private void getUserStarredRepos() {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {
            // Get the user starred repos
            GitHubService.getRepos(userName, true);

        } else {
            // If we don't have internet connection try fetching all user repos from the local storage
            getReposFromLocalStorage();
        }
    }

    private void getUserFromLocalStorage() {
        if (TextUtils.isEmpty(userName)) {
            // Get the logged in user
            user = DAOUsers.getLoggedUser();
        } else {
            // Get the user by user name
            user = DAOUsers.getUserByName(userName);
        }

        if (user == null) {
            onUserLoadingFinishListener.onUserLoadingFailed();

        } else {
            getUserRepos();
        }
    }

    private void getReposFromLocalStorage() {
        long userId = DAOUsers.getUserIdByName(userName);
        ArrayList<DBORepo> repos = new ArrayList<>();
        if (userId != -1) {
            repos.addAll(DAORepos.getAllRepos(userId));
        }
        onUserLoadingFinishListener.onUserLoadingCompleted(user, repos);
    }
}
