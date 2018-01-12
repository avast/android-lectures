package com.avast.example.android.github;

import javax.inject.Inject;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import com.avast.example.android.github.data.Preferences;
import com.avast.example.android.github.di.ApiModule;
import com.avast.example.android.github.di.AppComponent;
import com.avast.example.android.github.di.ApplicationModule;
import com.avast.example.android.github.di.DaggerAppComponent;

/**
 * @author Tomáš Kypta (kypta@avast.com)
 * @author Lukas Prokop (prokop@avast.com)
 */
public abstract class BaseApplication extends Application {

    public static final String LOG_TAG = "GitHubApp";

    @Inject
    Preferences mPreferences;

    AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // here you can od some global app initializations
        initLeakCannary();
        initStetho();
        initDagger();
        getComponent().inject(this);

        Log.d(LOG_TAG, "App is started for " + mPreferences.getAppLaunches());
        mPreferences.incremetAppLaunches();
    }

    private void initDagger() {
        mComponent = DaggerAppComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .apiModule(new ApiModule())
            .build();
    }

    private void initLeakCannary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    protected abstract void initStetho();

    public AppComponent getComponent() {
        return mComponent;
    }
}