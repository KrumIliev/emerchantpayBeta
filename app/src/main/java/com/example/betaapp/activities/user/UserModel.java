package com.example.betaapp.activities.user;

import android.content.Context;
import android.text.TextUtils;

import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.utils.ConnectivityMonitor;

public class UserModel implements UserInterfaces.Model, ReceiverUser.OnUserLoadingCompleted {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final UserInterfaces.Model.OnUserLoadingFinishListener onUserLoadingFinishListener;

    private final ReceiverUser receiverUser;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserModel(UserInterfaces.Model.OnUserLoadingFinishListener onUserLoadingFinishListener) {
        this.onUserLoadingFinishListener = onUserLoadingFinishListener;
        receiverUser = new ReceiverUser(this);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void register(Context context) {
        receiverUser.register(context);
    }

    @Override
    public void unregister(Context context) {
        receiverUser.unregister(context);
    }

    @Override
    public void getUserData(String userName) {
        if (ConnectivityMonitor.getInstance().hasInternetConnection()) {

            if (TextUtils.isEmpty(userName)) {
                // Get the logged in user
                GitHubService.getLoggedUser();
            } else {
                // Get the user by user name
                GitHubService.getUser(userName);
            }
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
}
