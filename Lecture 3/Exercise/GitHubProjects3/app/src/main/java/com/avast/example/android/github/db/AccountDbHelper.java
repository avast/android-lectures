package com.avast.example.android.github.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Tomáš Kypta (kypta)
 */
public class AccountDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GitHubAccounts.db";

    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_CREATE = "create table "
        + TABLE_ACCOUNTS + "("
        + COLUMN_ID + " integer primary key autoincrement, "
        + COLUMN_NAME + " text unique not null" +
        ");";

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(db);
    }
}
