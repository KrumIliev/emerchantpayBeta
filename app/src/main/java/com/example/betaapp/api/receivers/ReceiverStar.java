package com.example.betaapp.api.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.betaapp.utils.BaseApplication;

public class ReceiverStar extends ReceiverBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ReceiverStar.class.getSimpleName();

    private static final String ACTION_STAR_SUCCESSFUL = "com.example.betaapp.api.receivers.ACTION_STAR_SUCCESSFUL";

    private static final String ACTION_STAR_FAILED = "com.example.betaapp.api.receivers.ACTION_STAR_FAILED";

    private final OnStarCompletedListener onStarCompletedListener;

    public ReceiverStar(OnStarCompletedListener onStarCompletedListener) {
        super();
        this.onStarCompletedListener = onStarCompletedListener;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected void addActions(IntentFilter filter) {
        filter.addAction(ACTION_STAR_SUCCESSFUL);
        filter.addAction(ACTION_STAR_FAILED);
    }

    @Override
    protected void onReceive(Context context, String action, Intent data) {
        Log.d(LOG_TAG, "onReceive : " + action);
        switch (action) {
            case ACTION_STAR_SUCCESSFUL:
                onStarCompletedListener.onStarSuccessful();
                break;

            case ACTION_STAR_FAILED:
                onStarCompletedListener.onStarFailed();
                break;
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static void broadcastStarSuccessful() {
        BaseApplication.getContext().sendBroadcast(new Intent(ACTION_STAR_SUCCESSFUL));
    }

    public static void broadcastStarFailed() {
        BaseApplication.getContext().sendBroadcast(new Intent(ACTION_STAR_FAILED));
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    public interface OnStarCompletedListener {
        void onStarSuccessful();

        void onStarFailed();
    }
}
