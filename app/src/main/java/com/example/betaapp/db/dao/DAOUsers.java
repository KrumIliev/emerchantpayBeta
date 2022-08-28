package com.example.betaapp.db.dao;

import android.net.Uri;

import com.example.betaapp.db.tables.TableUsers;

public class DAOUsers extends DAOBase {

    public DAOUsers() {
        super(TableUsers.TABLE_NAME, TableUsers.CONTENT_URI);
    }
}
