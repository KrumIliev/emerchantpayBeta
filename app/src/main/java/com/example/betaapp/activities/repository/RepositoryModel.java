package com.example.betaapp.activities.repository;

import android.content.Context;

import com.example.betaapp.activities.login.LoginModel;
import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverStar;
import com.example.betaapp.db.models.DBORepo;

public class RepositoryModel implements RepositoryInterfaces.Model, ReceiverStar.OnStarCompletedListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = RepositoryModel.class.getSimpleName();

    private final ReceiverStar receiverStar;

    private final DBORepo repo;

    private final onStaringCompletedListener onStaringCompletedListener;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public RepositoryModel(onStaringCompletedListener onStaringCompletedListener, DBORepo repo) {
        this.onStaringCompletedListener = onStaringCompletedListener;
        this.repo = repo;
        this.receiverStar = new ReceiverStar(this);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void starRepo() {
        GitHubService.starRepo(repo, !repo.isStarred());
    }

    @Override
    public void register(Context context) {
        receiverStar.register(context);
    }

    @Override
    public void unregister(Context context) {
        receiverStar.unregister(context);
    }

    @Override
    public void onStarSuccessful() {
        repo.setStarred(!repo.isStarred());
        onStaringCompletedListener.onStarSuccessful(repo.isStarred());
    }

    @Override
    public void onStarFailed() {
        onStaringCompletedListener.onStarFailed();
    }
}
