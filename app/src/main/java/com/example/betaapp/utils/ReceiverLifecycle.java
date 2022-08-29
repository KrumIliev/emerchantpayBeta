package com.example.betaapp.utils;

import android.content.Context;

public interface ReceiverLifecycle {
    void onStart(Context context);

    void onStop(Context context);
}
