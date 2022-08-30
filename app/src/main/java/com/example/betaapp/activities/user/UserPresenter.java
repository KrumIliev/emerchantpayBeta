package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;

import java.util.ArrayList;

public class UserPresenter implements UserInterfaces.Presenter, UserInterfaces.Model.OnUserLoadingFinishListener, UserInterfaces.Model.OnReposLoadingFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final UserInterfaces.View view;

    private final UserInterfaces.Model model;

    private DBOUser user;

    private ArrayList<DBORepo> repos;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public UserPresenter(UserInterfaces.View view) {
        this.view = view;
        this.model = new UserModel(this, this);
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
        user = null;
        repos = new ArrayList<>();
        this.model.getUserData(userName);
        this.view.showLoading();
    }

    @Override
    public void onUserLoadingCompleted(DBOUser user) {
        this.user = user;
        model.getUserRepos(user.getUserName());
    }

    @Override
    public void onUserLoadingFailed() {
        view.hideLoading();
        view.showUserError();
    }

    @Override
    public void onReposLoadingCompleted(ArrayList<DBORepo> repos) {
        view.hideLoading();
        this.repos.addAll(repos);
        view.showUserData(user, this.repos);
    }

    @Override
    public void onReposLoadingFailed() {
        view.hideLoading();
        view.showUserData(user, repos);
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
}
