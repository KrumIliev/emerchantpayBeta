package com.example.betaapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.betaapp.db.tables.TableRepos;
import com.example.betaapp.db.tables.TableUsers;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "emp_db";

    public DatabaseHelper(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating database");

        db.execSQL(TableUsers.getCreateSql());
        db.execSQL(TableRepos.getCreateSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(LOG_TAG, "Dropping database");
        // Drop all tables
        db.execSQL(TableUsers.getDropSql());
        db.execSQL(TableRepos.getDropSql());

        // Recreate all tables
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // This makes the database faster especially for multi-threaded operations.
        setWriteAheadLoggingEnabled(true);
    }
}
