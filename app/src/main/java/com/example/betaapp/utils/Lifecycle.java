package com.example.betaapp.utils;

import android.content.Context;

public interface Lifecycle {
    void onStart(Context context);

    void onStop(Context context);

    void onDestroy();
}
