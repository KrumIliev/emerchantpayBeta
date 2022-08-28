package com.example.betaapp.activities.login;

import android.content.Context;
import android.net.Uri;

import com.example.betaapp.utils.Receiver;

/**
 * MVP interfaces for Login
 */
public interface Login {

    interface View {
        void showLoading();

        void loadingComplete(boolean isSuccessful);
    }

    interface Model extends Receiver {
        interface OnLoginFinishListener {
            void onLoginCompleted(boolean isSuccessful);
        }

        void login(Context context);

        void extractCode(Uri uri);
    }

    interface Presenter {
        void onLoginClick(Context context);

        void onStart(Context context);

        void onStop(Context context);

        void onResume(Uri uri);

        void onDestroy();
    }
}
