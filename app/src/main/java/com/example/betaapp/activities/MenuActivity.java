package com.example.betaapp.activities;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.betaapp.R;
import com.example.betaapp.activities.login.LoginActivity;
import com.example.betaapp.activities.user.UserActivity;
import com.example.betaapp.utils.Cache;

/**
 * Helper class to handle all menus
 */
public abstract class MenuActivity extends AppCompatActivity {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    protected static final int MENU_USER = R.menu.main_user;

    protected static final int MENU_MAIN = R.menu.main_user;

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(setMenu(), menu);
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

    // -------------------------------------------------------------------------------
    // Protected
    // -------------------------------------------------------------------------------

    protected abstract int setMenu();
}
