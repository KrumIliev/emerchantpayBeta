package com.example.betaapp.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.betaapp.BaseApplication;
import com.example.betaapp.db.models.DBOUser;
import com.example.betaapp.db.tables.TableUsers;

import java.util.Arrays;

public class DAOUsers extends DAOBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = DAOUsers.class.getSimpleName();

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public DAOUsers() {
        super(TableUsers.TABLE_NAME, TableUsers.CONTENT_URI);
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static long insertUser(DBOUser user) {
        ContentValues values = new ContentValues();
        values.put(TableUsers.USER_NAME, user.getUserName());
        values.put(TableUsers.USER_DISPLAY_NAME, user.getDisplayName());
        values.put(TableUsers.USER_AVATAR_URL, user.getAvatarUrl());
        values.put(TableUsers.USER_FOLLOWERS, user.getFollowers());
        values.put(TableUsers.USER_FOLLOWING, user.getFollowing());
        values.put(TableUsers.USER_IS_LOGGED, user.isLogged());

        Uri uri = BaseApplication.getContext().getContentResolver().insert(TableUsers.CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    /**
     * @param userName User name to search or null to get logged user id
     */
    public static long getUserIdByName(String userName) {
        Cursor cursor = null;
        long id = -1;

        try {
            String where;
            String[] whereArgs;
            if (TextUtils.isEmpty(userName)) {
                where = TableUsers.USER_IS_LOGGED + "=?";
                whereArgs = new String[]{Integer.toString(1)};

            } else {
                where = TableUsers.USER_NAME + "=?";
                whereArgs = new String[]{userName};
            }

            cursor = BaseApplication.getContext().getContentResolver().query(TableUsers.CONTENT_URI, null, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndexOrThrow(TableUsers._ID));
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return id;
    }

    public static DBOUser getLoggedUser() {
        String where = TableUsers.USER_IS_LOGGED + "=?";
        String[] whereArgs = {Integer.toString(1)};
        return getUser(where, whereArgs);
    }

    public static DBOUser getUserByName(String userName) {
        String where = TableUsers.USER_NAME + "=?";
        String[] whereArgs = {userName};
        return getUser(where, whereArgs);
    }

    private static DBOUser getUser(String where, String[] whereArgs) {
        Cursor cursor = null;
        DBOUser user = null;

        try {
            cursor = BaseApplication.getContext().getContentResolver().query(TableUsers.CONTENT_URI, null, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                user = getUserFromCursor(cursor);
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

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private static DBOUser getUserFromCursor(Cursor cursor) {
        DBOUser user = new DBOUser();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TableUsers._ID)));
        user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_NAME)));
        user.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_DISPLAY_NAME)));
        user.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableUsers.USER_AVATAR_URL)));
        user.setFollowers(cursor.getInt(cursor.getColumnIndexOrThrow(TableUsers.USER_FOLLOWERS)));
        user.setFollowing(cursor.getInt(cursor.getColumnIndexOrThrow(TableUsers.USER_FOLLOWING)));
        return user;
    }
}
