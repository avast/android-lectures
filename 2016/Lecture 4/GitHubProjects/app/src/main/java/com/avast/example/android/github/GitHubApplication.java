package com.avast.example.android.github;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import dagger.internal.DaggerCollections;

import com.avast.example.android.github.data.Preferences;
import com.avast.example.android.github.di.ApiModule;
import com.avast.example.android.github.di.AppComponent;
import com.avast.example.android.github.di.DaggerAppComponent;

/**
 * @author Tomáš Kypta (kypta@avast.com)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class GitHubApplication extends Application {

    public static final String LOG_TAG = "GitHubApp";
    Preferences mPreferences;

    AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mPreferences = new Preferences(this);
        Log.d(LOG_TAG, "App is started for " + mPreferences.getAppLaunches());
        mPreferences.incremetAppLaunches();

        // here you can od some global app initializations
        initLeakCannary();
        initStetho();
        initDagger();
    }

    private void initDagger() {
        mComponent = DaggerAppComponent.builder()
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

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}