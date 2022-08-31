package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.utils.BaseApplication;

public class ReceiverAuthentication extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverAuthentication.class.getSimpleName();

    private static final String ACTION_AUTH_COMPLETED = "com.example.betaapp.api.receivers.ACTION_AUTH_COMPLETED";

    private static final String EXTRA_RESULT = "com.example.betaapp.api.receivers.EXTRA_RESULT";

    private final AuthenticationCompleteListener completeListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public ReceiverAuthentication(AuthenticationCompleteListener listener) {
        super();
        this.completeListener = listener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void addActions(IntentFilter filter) {
        filter.addAction(ACTION_AUTH_COMPLETED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        if (action.contentEquals(ACTION_AUTH_COMPLETED)) {
            completeListener.onAuthenticationCompleted(data.getBooleanExtra(EXTRA_RESULT, false));
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastAuthenticationCompleted(boolean isSuccessful) {
        Intent intent = new Intent(ACTION_AUTH_COMPLETED);
        intent.putExtra(EXTRA_RESULT, isSuccessful);
        BaseApplication.getContext().sendBroadcast(intent);
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface AuthenticationCompleteListener {
        void onAuthenticationCompleted(boolean isSuccessful);
    }
}
