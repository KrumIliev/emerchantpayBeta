package com.example.betaapp.db.tables;

import android.net.Uri;

public class TableUsers extends BaseTable {

    public static final String TABLE_NAME = "users";
    public static final Uri CONTENT_URI = generateContentUri(TABLE_NAME);
    public static final int URI_CODE = 1;

    public static final String USER_NAME = "user_name";
    public static final String USER_DISPLAY_NAME = "display_name";
    public static final String USER_AVATAR_URL = "avatar_url";
    public static final String USER_FOLLOWERS = "followers";
    public static final String USER_FOLLOWING = "following";
    public static final String USER_IS_LOGGED = "isLogged";

    public static String getCreateSql() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE ").append(TABLE_NAME).append(" (");
        sqlBuilder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(USER_NAME).append(" TEXT, ");
        sqlBuilder.append(USER_DISPLAY_NAME).append(" TEXT, ");
        sqlBuilder.append(USER_AVATAR_URL).append(" TEXT, ");
        sqlBuilder.append(USER_FOLLOWERS).append(" INTEGER, ");
        sqlBuilder.append(USER_FOLLOWING).append(" INTEGER, ");
        sqlBuilder.append(USER_IS_LOGGED).append(" INTEGER DEFAULT 0, ");
        sqlBuilder.append("UNIQUE(").append(USER_NAME).append(") ON CONFLICT REPLACE);");

        return sqlBuilder.toString();
    }

    public static String getDropSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
        return sqlBuilder.toString();
    }
}
