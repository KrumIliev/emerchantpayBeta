package com.example.betaapp.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.betaapp.BaseApplication;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.db.tables.TableUsers;

import java.util.Arrays;

public class DAOUsers extends DAOBase {

    private static final String LOG_TAG = DAOUsers.class.getSimpleName();

    public DAOUsers() {
        super(TableUsers.TABLE_NAME, TableUsers.CONTENT_URI);
    }

    public static long insertUser(DBOUser user) {
        ContentValues values = new ContentValues();
        values.put(TableUsers.USER_NAME, user.getUserName());
        values.put(TableUsers.USER_DISPLAY_NAME, user.getDisplayName());
        values.put(TableUsers.USER_AVATAR_URL, user.getAvatarUrl());
        values.put(TableUsers.USER_FOLLOWERS, user.getFollowers());
        values.put(TableUsers.USER_FOLLOWING, user.getFollowing());

        Uri uri = BaseApplication.getContext().getContentResolver().insert(TableUsers.CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    public static DBOUser getUserById(long id) {
        Cursor cursor = null;
        DBOUser user = null;

        try {
            String where = TableUsers._ID + "=?";
            String[] whereArgs = {Long.toString(id)};

            cursor = BaseApplication.getContext().getContentResolver().query(TableUsers.CONTENT_URI, null, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                user = new DBOUser();
                user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TableUsers._ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_NAME)));
                user.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_DISPLAY_NAME)));
                user.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_AVATAR_URL)));
                user.setFollowers(cursor.getInt(cursor.getColumnIndexOrThrow(TableUsers.USER_FOLLOWERS)));
                user.setFollowing(cursor.getInt(cursor.getColumnIndexOrThrow(TableUsers.USER_FOLLOWING)));
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }
}
