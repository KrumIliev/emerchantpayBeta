package com.example.betaapp.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.betaapp.R;
import com.example.betaapp.activities.user.UserActivity;
import com.example.betaapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements LoginInterfaces.View {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private LoginInterfaces.Presenter presenter;

    private ActivityLoginBinding viewBinding;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

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
        if (isSuccessful) {
            UserActivity.startActivity(this, null);

        } else {
            Toast.makeText(this, R.string.login_failed_message, Toast.LENGTH_SHORT).show();
            viewBinding.loginButton.setVisibility(View.VISIBLE);
            viewBinding.loginProgress.setVisibility(View.GONE);
        }
    }
}
