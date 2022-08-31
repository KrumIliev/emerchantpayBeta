package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.utils.BaseApplication;

public class ReceiverUser extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverUser.class.getSimpleName();

    private static final String ACTION_USER_LOADED = "com.example.betaapp.api.receivers.ACTION_USER_LOADED";

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
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        if (action.contentEquals(ACTION_USER_LOADED)) {
            completeListener.onUserLoaded();
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastUserLoaded() {
        BaseApplication.getContext().sendBroadcast(new Intent(ACTION_USER_LOADED));
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnUserLoadingCompleted {
        void onUserLoaded();
    }
}
