package com.example.betaapp.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.betaapp.api.actions.GitHubRequest;

import java.util.Arrays;
import java.util.List;

public class GitHubAuthenticator {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GitHubAuthenticator.class.getSimpleName();

    private static final String URL = "/login/oauth/authorize";

    private static final String CLIENT_SECRET = "58e3406b37f34a19865a009c7e6462236bd25d8a";

    private static final String CLIENT_ID = "6a1c6c6de961e8655b5d";

    private static final String REDIRECT_URL = "oauth://callback";

    private static final List<String> SCOPE = Arrays.asList("user", "repo");

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public void authenticate(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(generateCodeURL()));
        context.startActivity(intent);
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public void extractCodeAndGetToken(Uri uri) {
        if (uri != null && uri.toString().startsWith(GitHubRequest.REDIRECT_URL)) {
            String code = uri.getQueryParameter("code");
            Log.d(LOG_TAG, code);
            GitHubService.getToken(code);
        }
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * Generates the following URL
     * https://github.com/login/oauth/authorize?client_id=id&scope=scope&redirect_url=url
     */
    private String generateCodeURL() {
        StringBuilder builder = new StringBuilder();

        // Generate base url
        builder.append(GitHubRequest.BASE_HTTP_URL);
        builder.append(URL);

        // Add client id
        builder.append("?client_id=");
        builder.append(CLIENT_ID);

        // Add scope
        builder.append("&scope=");
        for (int i = 0; i < SCOPE.size(); i++) {
            builder.append(SCOPE.get(i));
            if (i < SCOPE.size() - 1) {
                builder.append("%20");
            }
        }

        // Add redirect URL
        builder.append("&redirect_url=");
        builder.append(REDIRECT_URL);

        return builder.toString();
    }
}
