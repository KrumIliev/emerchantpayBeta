package com.example.betaapp.activities;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.betaapp.R;
import com.example.betaapp.activities.login.LoginActivity;
import com.example.betaapp.activities.user.UserActivity;
import com.example.betaapp.utils.Cache;

public class BaseActivity extends AppCompatActivity {

    private int menuRes = R.menu.main_user;

    protected void setMenuRes(int menuRes) {
        this.menuRes = menuRes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuRes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                UserActivity.startActivity(this, null);
                return true;

            case R.id.menu_logout:
                Cache.gitHubToken = null;
                LoginActivity.startActivity(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
