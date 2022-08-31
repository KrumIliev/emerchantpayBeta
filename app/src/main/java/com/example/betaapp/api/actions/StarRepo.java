package com.example.betaapp.api.actions;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.betaapp.api.receivers.ReceiverStar;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.models.DBORepo;

public class StarRepo extends GitHubRequest<Void> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = StarRepo.class.getSimpleName();

    public static final String EXTRA_REPO = "com.example.betaapp.api.actions.EXTRA_REPO";

    public static final String EXTRA_STAR = "com.example.betaapp.api.actions.EXTRA_STAR";

    private final DBORepo repo;

    private final boolean starRepo;

    public StarRepo(Intent intent) {
        super(getMethod(intent), getURL(intent), true);
        repo = (DBORepo) intent.getSerializableExtra(EXTRA_REPO);
        starRepo = intent.getBooleanExtra(EXTRA_STAR, false);
    }

    @Override
    protected Void getRequest() {
        return null;
    }

    @Override
    protected void onRequestSuccess(String response) {
        DAORepos.updateStarStatus(repo.getId(), starRepo);
        ReceiverStar.broadcastStarSuccessful();
    }

    @Override
    protected void onRequestFailed(VolleyError volleyError) {
        Log.e(LOG_TAG, volleyError.getMessage());
        ReceiverStar.broadcastStarSuccessful();
    }

    /**
     * @return Formatted URL
     * <p> -> https://api.github.com/users/USERNAME
     * <p> -> https://api.github.com/user
     */
    private static String getURL(Intent intent) {
        DBORepo repo = (DBORepo) intent.getSerializableExtra(EXTRA_REPO);
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_API_URL);
        builder.append("/user/starred/");
        builder.append(repo.getOwner());
        builder.append("/");
        builder.append(repo.getName());
        return builder.toString();
    }

    /**
     * Star and unstar requests are the same just use different method
     * To star an repository use PUT
     * To unstar an repository use DELETE
     */
    private static int getMethod(Intent intent) {
        return intent.getBooleanExtra(EXTRA_STAR, false) ? Method.PUT : Method.DELETE;
    }
}
