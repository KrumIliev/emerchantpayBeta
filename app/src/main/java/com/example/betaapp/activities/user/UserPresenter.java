package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;

import java.util.ArrayList;

public class UserPresenter implements
        UserInterfaces.Presenter,
        UserInterfaces.Model.OnUserLoadingFinishListener,
        UserInterfaces.Model.OnReposLoadingFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final UserInterfaces.View view;

    private final UserInterfaces.Model model;

    private DBOUser user;

    private final String userName;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserPresenter(UserInterfaces.View view, String userName) {
        this.view = view;
        this.model = new UserModel(this, this);
        this.userName = userName;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void onStart(Context context) {
        model.register(context);
    }

    @Override
    public void onStop(Context context) {
        model.unregister(context);
    }

    @Override
    public void getUserData() {
        user = null;
        this.model.getUserData(userName);
        this.view.showLoading();
    }

    @Override
    public void onUserLoadingCompleted(DBOUser user) {
        this.user = user;
        model.getUserRepos(userName);
    }

    @Override
    public void onUserLoadingFailed() {
        view.hideLoading();
        view.showUserError();
    }

    @Override
    public void onReposLoadingCompleted(ArrayList<DBORepo> repos) {
        view.hideLoading();
        view.showUserData(user, repos);
    }

    @Override
    public void onFollowersClick(Context context, String userName) {
        //TODO open user search activity
    }

    @Override
    public void onFollowingClick(Context context, String userName) {
        //TODO open user search activity
    }

    @Override
    public void onRepositoryClick(Context context, DBORepo repo) {
        //TODO open user search activity
    }
}
