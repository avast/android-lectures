package com.avast.example.android.github.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.otto.Bus;

import com.avast.example.android.github.event.DbUpdatedEvent;
import com.avast.example.android.github.model.Account;

/**
 * @author Tomáš Kypta (kypta)
 */
@Singleton
public class AccountDataSource {

    private SQLiteDatabase mDatabase;
    private AccountDbHelper mDbHelper;
    private Bus mBus;

    private static String[] sAllColumns = {
        AccountDbHelper.COLUMN_ID,
        AccountDbHelper.COLUMN_NAME
    };

    // this will automatically add the class ass provision to the component
    // but it cannot be overridden for tests
    // constructor arguments are automatically provided by the component
    @Inject
    public AccountDataSource(Context context, Bus bus) {
        mDbHelper = new AccountDbHelper(context);
        mBus = bus;
    }

    public void open() {
        try {
            mDatabase = mDbHelper.getWritableDatabase();
        } catch (Exception e) {
            // log a problem
        }
    }

    public void close() {
        mDbHelper.close();
    }

    public boolean addAccount(String account) {
        ContentValues cv = new ContentValues();
        cv.put(AccountDbHelper.COLUMN_NAME, account);
        boolean success = mDatabase.insert(AccountDbHelper.TABLE_ACCOUNTS, null, cv) != -1;
        // TODO task 10
        return success;
    }

    public List<Account> listAccounts() {
        Cursor cursor = listAccountsWithCursor();

        List<Account> users = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(AccountDbHelper.COLUMN_NAME));
            Account user = new Account();
            user.setName(name);
            users.add(user);

            cursor.moveToNext();
        }

        cursor.close();

        return users;
    }

    public Cursor listAccountsWithCursor() {
        return mDatabase.query(AccountDbHelper.TABLE_ACCOUNTS, sAllColumns,
            null, null, null, null, AccountDbHelper.COLUMN_NAME + " ASC");
    }

    /**
     *
     * @param account
     * @return Number of erased db rows.
     */
    public int removeAccount(String account) {
        int removed = mDatabase.delete(AccountDbHelper.TABLE_ACCOUNTS,
            AccountDbHelper.COLUMN_NAME + " = ?",
            new String[]{account});
        // TODO task 10
        return removed;
    }
}
