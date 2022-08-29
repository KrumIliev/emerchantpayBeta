package com.example.betaapp.activities.login;

import android.content.Context;
import android.net.Uri;

import com.example.betaapp.utils.ReceiverBinding;
import com.example.betaapp.utils.ReceiverLifecycle;

/**
 * MVP interfaces for Login page
 */
public interface LoginInterfaces {

    interface View {
        void showLoading();

        void loadingComplete(boolean isSuccessful);
    }

    interface Model extends ReceiverBinding {
        interface OnLoginFinishListener {
            void onLoginCompleted(boolean isSuccessful);
        }

        void login(Context context);

        void extractCode(Uri uri);
    }

    interface Presenter extends ReceiverLifecycle {
        void onLoginClick(Context context);

        void onResume(Uri uri);

        void onDestroy();
    }
}
