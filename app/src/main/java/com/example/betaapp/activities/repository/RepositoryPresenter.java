package com.example.betaapp.activities.repository;

import android.content.Context;

import com.example.betaapp.db.models.DBORepo;

public class RepositoryPresenter implements RepositoryInterfaces.Presenter, RepositoryInterfaces.Model.onStaringCompletedListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = RepositoryPresenter.class.getSimpleName();

    private RepositoryInterfaces.View view;

    private final RepositoryInterfaces.Model model;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public RepositoryPresenter(RepositoryInterfaces.View view, DBORepo repo) {
        this.view = view;
        model = new RepositoryModel(this, repo);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void onStart(Context context) {
        model.register(context);
    }

    @Override
    public void onStop(Context context) {
        model.unregister(context);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onStarClicked() {
        if (view != null) {
            view.disableStarButton();
        }

        model.starRepo();
    }

    @Override
    public void onStarSuccessful(boolean isStarred) {
        if (view != null) {
            view.enableStarButton();
            view.updateStarStatus(isStarred);
        }
    }

    @Override
    public void onStarFailed() {
        if (view != null) {
            view.enableStarButton();
            view.showStarError();
        }
    }
}
