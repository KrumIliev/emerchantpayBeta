package com.example.betaapp.activities.login;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.betaapp.api.receivers.ReceiverAuthentication;
import com.example.betaapp.api.GitHubAuthenticator;

public class LoginModel implements Login.Model, ReceiverAuthentication.AuthenticationCompleteListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = LoginModel.class.getSimpleName();

    private final ReceiverAuthentication receiver;

    private final GitHubAuthenticator authenticator;

    private final OnLoginFinishListener onFinishListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public LoginModel(OnLoginFinishListener onFinishListener) {
        receiver = new ReceiverAuthentication(this);
        authenticator = new GitHubAuthenticator();
        this.onFinishListener = onFinishListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void login(Context context) {
        Log.d(LOG_TAG, "login");
        authenticator.authenticate(context);
    }

    @Override
    public void extractCode(Uri uri) {
        Log.d(LOG_TAG, "extractCode");
        authenticator.extractCodeAndGetToken(uri);
    }

    @Override
    public void onAuthenticationCompleted(boolean isSuccessful) {
        Log.d(LOG_TAG, "onAuthenticationCompleted: " + isSuccessful);
        if (onFinishListener != null) {
            onFinishListener.onLoginCompleted(isSuccessful);
        } else {
            Log.d(LOG_TAG, "onAuthenticationCompleted: onFinishListener is null");
        }
    }

    @Override
    public void register(Context context) {
        receiver.register(context);
    }

    @Override
    public void unregister(Context context) {
        receiver.unregister(context);
    }
}
