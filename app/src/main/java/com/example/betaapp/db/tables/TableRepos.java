package com.example.betaapp.db.tables;

import android.net.Uri;

public class TableRepos extends BaseTable {

    public static final String TABLE_NAME = "repos";
    public static final Uri CONTENT_URI = generateContentUri(TABLE_NAME);
    public static final int URI_CODE = 2;

    public static final String REPO_NAME = "name";
    public static final String REPO_DESCRIPTION = "description";
    public static final String REPO_IS_STARRED = "is_starred";
    public static final String REPO_USER_ID = "user_id";

    public static String getCreateSql() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE ").append(TABLE_NAME).append(" (");
        sqlBuilder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(REPO_NAME).append(" TEXT, ");
        sqlBuilder.append(REPO_DESCRIPTION).append(" TEXT, ");
        sqlBuilder.append(REPO_IS_STARRED).append(" INTEGER DEFAULT 0, ");
        sqlBuilder.append(REPO_USER_ID).append(" INTEGER NOT NULL, ");
        sqlBuilder.append("UNIQUE(").append(REPO_NAME).append(",").append(REPO_USER_ID).append(") ON CONFLICT REPLACE);");

        return sqlBuilder.toString();
    }

    public static String getDropSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
        return sqlBuilder.toString();
    }
}
