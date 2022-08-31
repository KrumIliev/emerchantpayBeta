package com.example.betaapp.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.betaapp.utils.BaseApplication;
import com.example.betaapp.db.models.DBORepo;
import com.example.betaapp.db.tables.TableRepos;

import java.util.ArrayList;

public class DAORepos extends DAOBase {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = DAORepos.class.getSimpleName();

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public DAORepos() {
        super(TableRepos.TABLE_NAME, TableRepos.CONTENT_URI);
    }

    // -------------------------------------------------------------------------------
    // Public
    // -------------------------------------------------------------------------------

    public static long insertRepo(DBORepo repo) {
        ContentValues values = new ContentValues();
        values.put(TableRepos.REPO_NAME, repo.getName());
        values.put(TableRepos.REPO_DESCRIPTION, repo.getDescription());
        values.put(TableRepos.REPO_IS_STARRED, repo.isStarred());
        values.put(TableRepos.REPO_USER_ID, repo.getUserId());
        values.put(TableRepos.REPO_GIT_ID, repo.getGitId());
        values.put(TableRepos.REPO_OWNER, repo.getOwner());

        Uri uri = BaseApplication.getContext().getContentResolver().insert(TableRepos.CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    public static ArrayList<DBORepo> getAllRepos(long userId) {
        String where = TableRepos.REPO_USER_ID + "=?";
        String[] whereArgs = {Long.toString(userId)};
        return getRepos(where, whereArgs);
    }

    public static ArrayList<DBORepo> getRepos(String where, String[] whereArgs) {
        ArrayList<DBORepo> repos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = BaseApplication.getContext().getContentResolver().query(TableRepos.CONTENT_URI, null, where, whereArgs, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    repos.add(getRepoFromCursor(cursor));
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return repos;
    }

    public static DBORepo getRepoById(long id) {
        Cursor cursor = null;
        DBORepo repo = null;

        try {

            String where = TableRepos._ID + "=?";
            String[] whereArgs = {Long.toString(id)};

            cursor = BaseApplication.getContext().getContentResolver().query(TableRepos.CONTENT_URI, null, where, whereArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                repo = getRepoFromCursor(cursor);
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return repo;
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private static DBORepo getRepoFromCursor(Cursor cursor) {
        DBORepo repo = new DBORepo();
        repo.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TableRepos._ID)));
        repo.setName(cursor.getString(cursor.getColumnIndexOrThrow(TableRepos.REPO_NAME)));
        repo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TableRepos.REPO_DESCRIPTION)));
        repo.setStarred(cursor.getInt(cursor.getColumnIndexOrThrow(TableRepos.REPO_IS_STARRED)) == 1);
        repo.setGitId(cursor.getLong(cursor.getColumnIndexOrThrow(TableRepos.REPO_GIT_ID)));
        repo.setOwner(cursor.getString(cursor.getColumnIndexOrThrow(TableRepos.REPO_OWNER)));
        return repo;
    }
}
