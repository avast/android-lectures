package com.avast.example.android.github.dagger;

import javax.inject.Singleton;

import dagger.Component;

import com.avast.example.android.github.dagger.module.AppModule;
import com.avast.example.android.github.ui.accounts.AccountActivity;
import com.avast.example.android.github.ui.accounts.AccountFragment;
import com.avast.example.android.github.ui.accounts.LoadAccountsAsyncTask;
import com.avast.example.android.github.ui.repo.LoadAccountDetailsAsyncTask;
import com.avast.example.android.github.ui.repo.RepoFragment;

/**
 * Main app component.
 *
 * @author Tomáš Kypta (kypta)
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectAccountActivity(AccountActivity activity);

    void injectAccountFragment(AccountFragment fragment);

    void injectRepoFragment(RepoFragment fragment);

    void injectLoadAccountsAsyncTask(LoadAccountsAsyncTask asyncTask);

    void injectLoadAccountDetailsAsyncTask(LoadAccountDetailsAsyncTask asyncTask);
}
