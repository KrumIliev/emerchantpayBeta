package com.example.betaapp.activities.repository;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.betaapp.activities.MenuActivity;

public class RepositoryActivity extends MenuActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setMenu() {
        return MENU_MAIN;
    }
}
