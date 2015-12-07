package com.avast.example.android.github.dagger.module;

import javax.inject.Singleton;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import dagger.Module;
import dagger.Provides;

import com.avast.example.android.github.api.GitHubApi;

/**
 * @author Tomáš Kypta (kypta)
 */
@Module
public class AppModule {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private Context mApplicationContext;

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Singleton
    @Provides
    protected Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    protected Bus provideBus() {
        return new Bus(ThreadEnforcer.MAIN);
    }

    protected GitHubApi provideGitHubApi() {
        // TODO task 8 - use ApiFactory
        return null;
    }
}
