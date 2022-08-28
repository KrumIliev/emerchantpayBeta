package com.example.betaapp.utils;

public class Cache {

    /**
     * GitHub OAuth token used for API calls. This is initialized in the login and cleared when the application stops.
     */
    public static String gitHubToken;

    /**
     * Workaround for a bug i couldn't fix with the login activity and OAuth.
     */
    public static boolean isLoggingIn = false;
}
