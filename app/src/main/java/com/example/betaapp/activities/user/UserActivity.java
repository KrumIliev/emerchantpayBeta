package com.example.betaapp.activities.user;

import android.os.Bundle;
import android.view.View;

import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.receivers.ReceiverUser;
import com.example.betaapp.databinding.ActivityUserBinding;
import com.example.betaapp.db.models.DBOUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity implements ReceiverUser.OnUserLoadingCompleted {

    private ActivityUserBinding viewBinding;
    private ReceiverUser receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        setSupportActionBar(viewBinding.toolbar);

        receiverUser = new ReceiverUser(this);

        showLoading();
        GitHubService.getLoggedUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiverUser.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiverUser.unregister(this);
    }

    @Override
    public void onUserLoaded(DBOUser user) {
        hideLoading();
        viewBinding.userText.setText(user.toString());
    }

    @Override
    public void onUserLoadingFailed() {
        hideLoading();
        viewBinding.userText.setText("User data failed to load!");
    }

    private void showLoading() {
        viewBinding.userText.setVisibility(View.GONE);
        viewBinding.userProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        viewBinding.userText.setVisibility(View.VISIBLE);
        viewBinding.userProgress.setVisibility(View.GONE);
    }
}