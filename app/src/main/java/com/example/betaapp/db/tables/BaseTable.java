package com.example.betaapp.db.tables;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.betaapp.db.DatabaseProvider;

public abstract class BaseTable implements BaseColumns {

    protected static Uri generateContentUri(String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("content://");
        builder.append(DatabaseProvider.AUTHORITY);
        builder.append("/");
        builder.append(tableName);
        return Uri.parse(builder.toString());
    }
}
