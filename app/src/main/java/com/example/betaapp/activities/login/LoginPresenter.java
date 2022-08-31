package com.example.betaapp.activities.login;

import android.content.Context;
import android.net.Uri;

import com.example.betaapp.utils.Cache;

public class LoginPresenter implements LoginInterfaces.Presenter, LoginInterfaces.Model.OnLoginFinishListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    private LoginInterfaces.View view;

    private LoginInterfaces.Model model;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public LoginPresenter(LoginInterfaces.View view) {
        this.view = view;
        this.model = new LoginModel(this);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void onLoginClick(Context context) {
        Cache.isLoggingIn = true;

        if (view != null) {
            view.showLoading();
        }
        model.login(context);
    }

    @Override
    public void onLoginCompleted(boolean isSuccessful) {
        Cache.isLoggingIn = false;

        if (view != null) {
            view.loadingComplete(isSuccessful);
        }
    }

    @Override
    public void onStart(Context context) {
        model.register(context);
    }

    @Override
    public void onStop(Context context) {
        model.unregister(context);
    }

    @Override
    public void onResume(Uri uri) {
        if (Cache.isLoggingIn && view != null) {
            view.showLoading();
        }
        model.extractCode(uri);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
