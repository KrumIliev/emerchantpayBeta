package com.example.betaapp.activities.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        viewBinding.userText.setVisibility(View.GONE);
        viewBinding.userProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        viewBinding.userText.setVisibility(View.VISIBLE);
        viewBinding.userProgress.setVisibility(View.GONE);
    }

    @Override
    public void showUserData(DBOUser user) {
        viewBinding.userText.setText(user.toString());
    }

    @Override
    public void showUserError() {
        viewBinding.userText.setText("User failed to load");
    }
}