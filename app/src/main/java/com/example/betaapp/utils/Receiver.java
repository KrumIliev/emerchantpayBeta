package com.example.betaapp.utils;

import android.content.Context;

public interface Receiver {

    void register(Context context);

    void unregister(Context context);
}
