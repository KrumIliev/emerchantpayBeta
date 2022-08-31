package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;

import java.util.ArrayList;

public class UserPresenter implements
        UserInterfaces.Presenter,
        UserInterfaces.Model.OnUserDataLoadFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final UserInterfaces.View view;

    private final UserInterfaces.Model model;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserPresenter(UserInterfaces.View view, String userName) {
        this.view = view;
        this.model = new UserModel(userName, this);
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
        this.model.getUserData();
        this.view.showLoading();
    }

    @Override
    public void onUserLoadingCompleted(DBOUser user, ArrayList<DBORepo> repos) {
        view.hideLoading();
        view.showUserData(user, repos);
    }

    @Override
    public void onUserLoadingFailed() {
        view.hideLoading();
        view.showUserError();
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
