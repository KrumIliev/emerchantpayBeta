package com.example.betaapp.activities.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.betaapp.R;
import com.example.betaapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements Login.View {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Login.Presenter presenter;

    private ActivityLoginBinding viewBinding;

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        presenter = new LoginPresenter(this);

        viewBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClick(LoginActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        presenter.onResume(getIntent().getData());
        super.onResume();
    }

    @Override
    protected void onStart() {
        presenter.onStart(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public void showLoading() {
        viewBinding.loginButton.setVisibility(View.GONE);
        viewBinding.loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingComplete(boolean isSuccessful) {
        // TODO trigger user activity
        if (isSuccessful) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }

        viewBinding.loginButton.setVisibility(View.VISIBLE);
        viewBinding.loginProgress.setVisibility(View.GONE);
    }
}
