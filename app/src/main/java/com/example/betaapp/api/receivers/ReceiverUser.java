package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.BaseApplication;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.models.DBOUser;

public class ReceiverUser extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverUser.class.getSimpleName();

    private static final String ACTION_USER_LOADED = "com.example.betaapp.api.receivers.ACTION_USER_LOADED";

    private static final String ACTION_USER_LOAD_FAILED = "com.example.betaapp.api.receivers.ACTION_USER_LOAD_FAILED";

    private static final String EXTRA_USER_DATA = "com.example.betaapp.api.receivers.EXTRA_USER_DATA";

    private static final String EXTRA_USER_NAME = "com.example.betaapp.api.receivers.EXTRA_USER_NAME";

    private final OnUserLoadingCompleted completeListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public ReceiverUser(OnUserLoadingCompleted completeListener) {
        super();
        this.completeListener = completeListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void addActions(IntentFilter filter) {
        filter.addAction(ACTION_USER_LOADED);
        filter.addAction(ACTION_USER_LOAD_FAILED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        switch (action) {
            case ACTION_USER_LOADED:
                completeListener.onUserLoaded((DBOUser) data.getSerializableExtra(EXTRA_USER_DATA));
                break;

            case ACTION_USER_LOAD_FAILED:
                completeListener.onUserLoadingFailed(data.getStringExtra(EXTRA_USER_NAME));
                break;
        }

    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastUserLoaded(DBOUser user) {
        Intent intent = new Intent(ACTION_USER_LOADED);
        intent.putExtra(EXTRA_USER_DATA, user);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    public static void broadcastUserLoadingFailed(String userName) {
        Intent intent = new Intent(ACTION_USER_LOAD_FAILED);
        intent.putExtra(EXTRA_USER_NAME, userName);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnUserLoadingCompleted {
        void onUserLoaded(DBOUser user);

        /**
         * @param userName the requested user name or null of retrieving logged user
         */
        void onUserLoadingFailed(String userName);
    }
}
