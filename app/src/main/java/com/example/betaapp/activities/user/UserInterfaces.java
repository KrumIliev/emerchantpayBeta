package com.example.betaapp.activities.user;

import android.content.Context;

import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.utils.ReceiverBinding;
import com.example.betaapp.utils.ReceiverLifecycle;

import java.util.ArrayList;

/**
 * MVP interfaces for User page
 */
public interface UserInterfaces {

    interface View {
        void showLoading();

        void hideLoading();

        void showUserData(DBOUser user, ArrayList<DBORepo> repos);

        void showUserError();
    }

    interface Model extends ReceiverBinding {
        interface OnUserLoadingFinishListener {
            void onUserLoadingCompleted(DBOUser user);

            void onUserLoadingFailed();
        }

        interface OnReposLoadingFinishListener {
            void onReposLoadingCompleted(ArrayList<DBORepo> repos);

            void onReposLoadingFailed();
        }

        void getUserData(String userName);

        void getUserRepos(String userName);
    }

    interface Presenter extends ReceiverLifecycle {
        void getUserData(String userName);

        void onFollowersClick(Context context);

        void onFollowingClick(Context context);

        void onRepositoryClick(Context context);
    }
}
