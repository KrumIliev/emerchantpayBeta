package com.example.betaapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Class for monitoring and storing internet connection status
 */
public class ConnectivityMonitor {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = ConnectivityMonitor.class.getSimpleName();

    private static ConnectivityMonitor instance;

    private boolean hasInternetConnection = false;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public static ConnectivityMonitor getInstance() {
        if (instance == null) {
            instance = new ConnectivityMonitor();
        }

        return instance;
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public void register(Context context) {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        connectivityManager.requestNetwork(networkRequest, networkCallback);
    }

    public boolean hasInternetConnection () {
        return hasInternetConnection;
    }

    // -------------------------------------------------------------------------------
    // Other
    // -------------------------------------------------------------------------------

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Log.d(LOG_TAG, "Connected to internet");
            hasInternetConnection = true;
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Log.d(LOG_TAG, "Lost connection to internet");
            hasInternetConnection = false;
        }
    };
}
