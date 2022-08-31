package com.example.betaapp.activities.user;

import android.content.Context;
import android.widget.Toast;

import com.example.betaapp.R;
import com.example.betaapp.activities.repository.RepositoryActivity;
import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;

import java.util.ArrayList;

public class UserPresenter implements
        UserInterfaces.Presenter,
        UserInterfaces.Model.OnUserDataLoadFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private UserInterfaces.View view;

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
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getUserData() {
        if (view != null) {
            this.view.showLoading();
        }

        this.model.getUserData();
    }

    @Override
    public void onUserLoadingCompleted(DBOUser user, ArrayList<DBORepo> repos) {
        if (view != null) {
            view.hideLoading();
            view.showUserData(user, repos);
        }
    }

    @Override
    public void onUserLoadingFailed() {
        if (view != null) {
            view.hideLoading();
            view.showUserError();
        }
    }

    @Override
    public void onFollowersClick(Context context, String userName) {
        Toast.makeText(context, R.string.todo, Toast.LENGTH_SHORT).show();
        //TODO open user search activity
    }

    @Override
    public void onFollowingClick(Context context, String userName) {
        Toast.makeText(context, R.string.todo, Toast.LENGTH_SHORT).show();
        //TODO open user search activity
    }

    @Override
    public void onRepositoryClick(Context context, DBORepo repo) {
        RepositoryActivity.startActivity(context, repo.getId());
    }
}
