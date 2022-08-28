package com.example.betaapp.db.dao;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.betaapp.db.DatabaseHelper;

public abstract class DAOBase {

    public static final String LOG_TAG = DAOBase.class.getSimpleName();

    private final String table;

    private final Uri tableUri;

    public DAOBase(String table, Uri tableUri) {
        this.table = table;
        this.tableUri = tableUri;
    }

    public Cursor query(@NonNull DatabaseHelper dbHelper, @NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
            qBuilder.setTables(table);

            cursor = qBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        } catch (Exception e) {
            Log.e(LOG_TAG + "/" + table, e.getMessage(), e);
            if (cursor != null) {
                cursor.close();
            }
        }

        return cursor;
    }

    public Uri insert(@NonNull DatabaseHelper dbHelper, @NonNull Uri uri, ContentValues contentValues) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insertOrThrow(table, null, contentValues);
            uri = ContentUris.withAppendedId(tableUri, rowId);

        } catch (Exception e) {
            Log.e(LOG_TAG + "/" + table, e.getMessage(), e);
        }

        return uri;
    }

    public int update(@NonNull DatabaseHelper dbHelper, @NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int count = 0;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            count = db.update(table, contentValues, selection, selectionArgs);

        } catch (Exception e) {
            Log.e(LOG_TAG + "/" + table, e.getMessage(), e);
        }

        return count;
    }

    public int delete(@NonNull DatabaseHelper dbHelper, @NonNull Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            count = db.delete(table, selection, selectionArgs);

        } catch (Exception e) {
            Log.e(LOG_TAG + "/" + table, e.getMessage(), e);
        }

        return count;
    }
}
