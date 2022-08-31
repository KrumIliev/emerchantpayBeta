package com.example.betaapp.activities.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.betaapp.R;
import com.example.betaapp.activities.MenuActivity;
import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverStar;
import com.example.betaapp.databinding.ActivityRepositoryBinding;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.models.DBORepo;

public class RepositoryActivity extends MenuActivity implements ReceiverStar.OnStarCompletedListener {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = RepositoryActivity.class.getSimpleName();

    private static final String EXTRA_REPO_ID = "com.example.betaapp.activities.repository.EXTRA_REPO_ID";

    private ActivityRepositoryBinding viewBinding;

    private DBORepo repo;

    private ReceiverStar receiverStar;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    /**
     * @param repoId Repository id to load into the screen
     */
    public static void startActivity(Context context, long repoId) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPO_ID, repoId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRepoFromIntent(getIntent());

        viewBinding = ActivityRepositoryBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        setSupportActionBar(viewBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiverStar = new ReceiverStar(this);

        viewBinding.repoName.setText(repo.getName());
        viewBinding.repoDescription.setText(repo.getDescription());
        setStarStatus();

        viewBinding.repoContributorsContainer.setOnClickListener(view -> {
            Toast.makeText(RepositoryActivity.this, R.string.todo, Toast.LENGTH_SHORT).show();
        });

        viewBinding.repoStarContainer.setOnClickListener(view -> {
            viewBinding.repoStarContainer.setEnabled(false);
            GitHubService.starRepo(repo, !repo.isStarred());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiverStar.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiverStar.unregister(this);
    }

    @Override
    protected int setMenu() {
        return MENU_MAIN;
    }

    @Override
    public void onStarSuccessful() {
        viewBinding.repoStarContainer.setEnabled(true);
        repo.setStarred(!repo.isStarred());
        setStarStatus();
    }

    @Override
    public void onStarFailed() {
        viewBinding.repoStarContainer.setEnabled(true);
        Toast.makeText(this, R.string.repo_star_error, Toast.LENGTH_LONG).show();
    }

    private void getRepoFromIntent(Intent intent) {
        long repoId = intent.getLongExtra(EXTRA_REPO_ID, -1);
        if (repoId == -1) {
            Log.e(LOG_TAG, "Repository id not found!");
            onBackPressed();
        }

        repo = DAORepos.getRepoById(repoId);
        if (repo == null) {
            Log.e(LOG_TAG, "Repository with id: " + repoId + " not found!");
            onBackPressed();
        }
    }

    private void setStarStatus() {
        viewBinding.reposStarText.setCompoundDrawablesWithIntrinsicBounds(repo.isStarred() ? R.drawable.ic_star : R.drawable.ic_star_empty, 0, 0, 0);
        viewBinding.reposStarText.setText(repo.isStarred() ? R.string.repo_un_star : R.string.repo_star);
    }
}
