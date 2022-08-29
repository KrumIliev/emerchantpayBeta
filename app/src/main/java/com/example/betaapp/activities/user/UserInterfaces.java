package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.utils.ReceiverBinding;
import com.example.betaapp.utils.ReceiverLifecycle;

/**
 * MVP interfaces for User page
 */
public interface UserInterfaces {

    interface View {
        void showLoading();

        void hideLoading();

        void showUserData(DBOUser user);

        void showUserError();
    }

    interface Model extends ReceiverBinding {
        interface OnUserLoadingFinishListener {
            void onUserLoadingCompleted(DBOUser user);

            void onUserLoadingFailed();
        }

        void getUserData(String userName);
    }

    interface Presenter extends ReceiverLifecycle {
        void getUserData(String userName);
    }
}
