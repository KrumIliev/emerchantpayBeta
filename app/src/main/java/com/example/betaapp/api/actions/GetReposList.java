package com.example.betaapp.api.actions;

import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseDTO;
import com.example.betaapp.api.models.response.ResponseRepo;
import com.example.betaapp.api.models.response.ResponseUser;

public class GetReposList extends GitHubRequest<RequestDTO, ResponseRepo> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GetReposList.class.getSimpleName();

    public static final String EXTRA_USER_NAME = "com.example.betaapp.api.actions.EXTRA_USER_NAME";

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GetReposList(Intent intent) {
        super(Method.GET, getUrl(intent.getStringExtra(EXTRA_USER_NAME)), ResponseRepo.class, true);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected RequestDTO getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(ResponseRepo response) {
        Log.d(LOG_TAG, response.toString());
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage(), volleyError);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    /**
     * @param userName User name of the user
     * @return Formatted URL -> https://api.github.com/users/USERNAME/repos
     */
    private static String getUrl(String userName) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API_URL);
        builder.append("/users/");
        builder.append(userName);
        builder.append("/repos");
        return builder.toString();
    }
}
