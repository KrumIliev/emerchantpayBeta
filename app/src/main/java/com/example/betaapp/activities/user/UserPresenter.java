package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.models.DBOUser;

public class UserPresenter implements UserInterfaces.Presenter, UserInterfaces.Model.OnUserLoadingFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final UserInterfaces.View view;

    private final UserInterfaces.Model model;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserPresenter(UserInterfaces.View view) {
        this.view = view;
        this.model = new UserModel(this);
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
    public void getUserData(String userName) {
        this.model.getUserData(userName);
        this.view.showLoading();
    }

    @Override
    public void onUserLoadingCompleted(DBOUser user) {
        view.hideLoading();
        view.showUserData(user);
    }

    @Override
    public void onUserLoadingFailed() {
        view.hideLoading();
        view.showUserError();
    }

    @Override
    public void onFollowersClick(Context context) {
        //TODO open user search activity
    }

    @Override
    public void onFollowingClick(Context context) {
        //TODO open user search activity
    }

    @Override
    public void onRepositoryClick(Context context) {
        //TODO open repository list
    }

    @Override
    public void onStarredClick(Context context) {
        //TODO open starred list
    }
}
