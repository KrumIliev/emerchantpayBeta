package com.example.betaapp.api.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

public abstract class ReceiverBase extends BroadcastReceiver {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private final IntentFilter filter;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public ReceiverBase() {
        this.filter = new IntentFilter();
        addActions(filter);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            onReceive(context, intent.getAction(), intent);
        }
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public void register(Context context) {
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    // -------------------------------------------------------------------------------
    // Protected
    // -------------------------------------------------------------------------------

    protected abstract void addActions(IntentFilter filter);

    protected abstract void onReceive(Context context, String action, Intent data);
}
