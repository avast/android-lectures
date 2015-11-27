package com.avast.example.android.github.ui.accounts;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.db.AccountDataSource;
import com.avast.example.android.github.db.AccountDbHelper;
import com.avast.example.android.github.model.Account;

/**
 * @author Tomáš Kypta (kypta)
 */
public class LoadAccountsAsyncTask extends AsyncTask<Void, Void, List<Account>> {

    public interface AccountListListener {
        void onDataLoaded(List<Account> accounts);
    }

    private WeakReference<AccountListListener> mListener;

    @Inject
    AccountDataSource mAccountDataSource;

    public LoadAccountsAsyncTask(Context context, AccountListListener listener) {
        mListener = new WeakReference<AccountListListener>(listener);
        ((GitHubApplication) context.getApplicationContext()).getAppComponent()
            .injectLoadAccountsAsyncTask(this);
    }

    @Override
    protected List<Account> doInBackground(Void... params) {
        return mAccountDataSource.listAccounts();
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        AccountListListener accountListListener = mListener.get();
        if (accountListListener != null) {
            accountListListener.onDataLoaded(accounts);
        }
    }
}
