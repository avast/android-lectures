package com.avast.example.android.github.ui.repo;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.AsyncTask;

import retrofit.RetrofitError;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.api.ApiFactory;
import com.avast.example.android.github.api.GitHubApi;
import com.avast.example.android.github.model.User;

/**
 * @author Tomáš Kypta (kypta)
 */
public class LoadAccountDetailsAsyncTask extends AsyncTask<String, Void, User> {

    public interface AccountDetailListener {
        void onDataLoaded(User user);
    }

    private WeakReference<AccountDetailListener> mListener;

    // TODO task 8
    GitHubApi mApi = ApiFactory.createGitHubApi();

    public LoadAccountDetailsAsyncTask(Context context, AccountDetailListener listener) {
        mListener = new WeakReference<AccountDetailListener>(listener);
        ((GitHubApplication) context.getApplicationContext()).getAppComponent()
            .injectLoadAccountDetailsAsyncTask(this);
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            return mApi.getUser(params[0]);
        } catch (RetrofitError e) {
            // something went wrong
            return null;
        }
    }

    @Override
    protected void onPostExecute(User user) {
        AccountDetailListener accountListListener = mListener.get();
        if (accountListListener != null) {
            accountListListener.onDataLoaded(user);
        }
    }
}
