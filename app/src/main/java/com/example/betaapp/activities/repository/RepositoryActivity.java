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
import com.example.betaapp.databinding.ActivityRepositoryBinding;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.models.DBORepo;

public class RepositoryActivity extends MenuActivity {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = RepositoryActivity.class.getSimpleName();

    private static final String EXTRA_REPO_ID = "com.example.betaapp.activities.repository.EXTRA_REPO_ID";

    private ActivityRepositoryBinding viewBinding;

    private DBORepo repo;

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

        viewBinding.repoName.setText(repo.getName());
        viewBinding.repoDescription.setText(repo.getDescription());
        setStarStatus();

        viewBinding.repoContributorsContainer.setOnClickListener(view -> {
            Toast.makeText(RepositoryActivity.this, R.string.todo, Toast.LENGTH_SHORT).show();
        });

        viewBinding.repoStarContainer.setOnClickListener(view -> {
            Toast.makeText(RepositoryActivity.this, R.string.todo, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected int setMenu() {
        return MENU_MAIN;
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
