package com.example.betaapp.activities.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.betaapp.R;
import com.example.betaapp.activities.MenuActivity;
import com.example.betaapp.databinding.ActivityRepositoryBinding;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.models.DBORepo;

public class RepositoryActivity extends MenuActivity implements RepositoryInterfaces.View {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = RepositoryActivity.class.getSimpleName();

    private static final String EXTRA_REPO_ID = "com.example.betaapp.activities.repository.EXTRA_REPO_ID";

    private ActivityRepositoryBinding viewBinding;

    private RepositoryInterfaces.Presenter presenter;

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

        viewBinding = ActivityRepositoryBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        setSupportActionBar(viewBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long repoId = getIntent().getLongExtra(EXTRA_REPO_ID, -1);
        DBORepo repo = DAORepos.getRepoById(repoId);
        presenter = new RepositoryPresenter(this, repo);

        viewBinding.repoName.setText(repo.getName());
        viewBinding.repoDescription.setText(repo.getDescription());
        setStarStatus(repo.isStarred());

        viewBinding.repoContributorsContainer.setOnClickListener(view -> {
            Toast.makeText(RepositoryActivity.this, R.string.todo, Toast.LENGTH_SHORT).show();
        });

        viewBinding.repoStarContainer.setOnClickListener(view -> {
            presenter.onStarClicked();
        });
    }

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop(this);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    protected int setMenu() {
        return MENU_MAIN;
    }

    @Override
    public void disableStarButton() {
        viewBinding.repoStarContainer.setEnabled(false);
    }

    @Override
    public void enableStarButton() {
        viewBinding.repoStarContainer.setEnabled(true);
    }

    @Override
    public void updateStarStatus(boolean isStarred) {
        setStarStatus(isStarred);
    }

    @Override
    public void showStarError() {
        Toast.makeText(this, R.string.repo_star_error, Toast.LENGTH_LONG).show();
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private void setStarStatus(boolean isStarred) {
        viewBinding.reposStarText.setCompoundDrawablesWithIntrinsicBounds(isStarred ? R.drawable.ic_star : R.drawable.ic_star_empty, 0, 0, 0);
        viewBinding.reposStarText.setText(isStarred ? R.string.repo_un_star : R.string.repo_star);
    }
}
