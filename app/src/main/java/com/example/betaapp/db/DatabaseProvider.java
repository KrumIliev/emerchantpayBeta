package com.example.betaapp.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.betaapp.BuildConfig;
import com.example.betaapp.db.dao.DAOBase;
import com.example.betaapp.db.dao.DAORepos;
import com.example.betaapp.db.dao.DAOUsers;
import com.example.betaapp.db.tables.TableRepos;
import com.example.betaapp.db.tables.TableUsers;

public class DatabaseProvider extends ContentProvider {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = DatabaseProvider.class.getSimpleName();

    public static final String AUTHORITY = "com.example.betaapp.DatabaseProvider";

    private UriMatcher uriMatcher;

    private DatabaseHelper dbHelper;

    // -------------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------------

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TableUsers.TABLE_NAME, TableUsers.URI_CODE);
        uriMatcher.addURI(AUTHORITY, TableRepos.TABLE_NAME, TableRepos.URI_CODE);

        dbHelper = new DatabaseHelper(getContext(), BuildConfig.VERSION_CODE);

        Log.d(LOG_TAG, "DatabaseProvider created");
        return true;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return getDao(uri).query(dbHelper, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return getDao(uri).insert(dbHelper, uri, contentValues);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return getDao(uri).update(dbHelper, uri, contentValues, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return getDao(uri).delete(dbHelper, uri, selection, selectionArgs);
    }

    // -------------------------------------------------------------------------------
    // Private
    // -------------------------------------------------------------------------------

    private DAOBase getDao(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TableUsers.URI_CODE:
                return new DAOUsers();

            case TableRepos.URI_CODE:
                return new DAORepos();

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
}
