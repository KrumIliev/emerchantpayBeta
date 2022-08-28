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

    private static final String EXTRA_USER_ID = "com.example.betaapp.api.receivers.EXTRA_USER_ID";

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
                long userId = data.getLongExtra(EXTRA_USER_ID, -1);
                if (userId != -1) {
                    completeListener.onUserLoaded(DAOUsers.getUserById(userId));
                } else {
                    completeListener.onUserLoadingFailed();
                }
                break;

            case ACTION_USER_LOAD_FAILED:
                completeListener.onUserLoadingFailed();
                break;
        }

    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastUserLoaded(long id) {
        Intent intent = new Intent(ACTION_USER_LOADED);
        intent.putExtra(EXTRA_USER_ID, id);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    public static void broadcastUserLoadingFailed() {
        BaseApplication.getContext().sendBroadcast(new Intent(ACTION_USER_LOAD_FAILED));
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnUserLoadingCompleted {
        void onUserLoaded(DBOUser user);

        void onUserLoadingFailed();
    }
}
