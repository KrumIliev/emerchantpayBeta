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
        interface OnUserDataLoadFinishListener {
            void onUserLoadingCompleted(DBOUser user, ArrayList<DBORepo> repos);

            void onUserLoadingFailed();
        }

        void getUserData();
    }

    interface Presenter extends ReceiverLifecycle {
        void getUserData();

        void onFollowersClick(Context context, String userName);

        void onFollowingClick(Context context, String userName);

        void onRepositoryClick(Context context, DBORepo repo);
    }
}
