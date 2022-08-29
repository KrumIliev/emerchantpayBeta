package com.example.betaapp.activities.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.databinding.ActivityUserBinding;
import com.example.betaapp.db.models.DBOUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity implements UserInterfaces.View {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String EXTRA_USER_NAME = "com.example.betaapp.activities.user.EXTRA_USER_NAME";

    private ActivityUserBinding viewBinding;

    private UserInterfaces.Presenter presenter;

    private String userName;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    /**
     * @param userName User to load into the screen, or null to load logged user
     */
    public static void startActivity(Context context, String userName) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userName);
        context.startActivity(intent);
    }

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        setSupportActionBar(viewBinding.toolbar);

        presenter = new UserPresenter(this);
        userName = getIntent().getStringExtra(EXTRA_USER_NAME);

        viewBinding.userStarredContainer.setOnClickListener(view -> presenter.onStarredClick(UserActivity.this));
        viewBinding.userRepositoriesContainer.setOnClickListener(view -> presenter.onRepositoryClick(UserActivity.this));
        viewBinding.userFollowingContainer.setOnClickListener(view -> presenter.onFollowingClick(UserActivity.this));
        viewBinding.userFollowersContainer.setOnClickListener(view -> presenter.onFollowersClick(UserActivity.this));
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserData(userName);
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        viewBinding.userDataContainer.setVisibility(View.GONE);
        viewBinding.userError.setVisibility(View.GONE);
        viewBinding.userProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        viewBinding.userDataContainer.setVisibility(View.VISIBLE);
        viewBinding.userError.setVisibility(View.GONE);
        viewBinding.userProgress.setVisibility(View.GONE);
    }

    @Override
    public void showUserData(DBOUser user) {
        viewBinding.userDisplayName.setText(user.getDisplayName());
        viewBinding.userName.setText(user.getUserName());
        Glide.with(this).load(user.getAvatarUrl()).into(viewBinding.userAvatar);
        viewBinding.userFollowersCount.setText(String.valueOf(user.getFollowers()));
        viewBinding.userFollowingCount.setText(String.valueOf(user.getFollowing()));
    }

    @Override
    public void showUserError() {
        viewBinding.userDataContainer.setVisibility(View.GONE);
        viewBinding.userProgress.setVisibility(View.GONE);
        viewBinding.userError.setVisibility(View.VISIBLE);
    }
}