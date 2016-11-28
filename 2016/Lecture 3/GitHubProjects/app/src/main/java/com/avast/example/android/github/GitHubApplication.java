package com.avast.example.android.github;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import com.avast.example.android.github.data.Preferences;

/**
 * @author Tomáš Kypta (kypta@avast.com)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class GitHubApplication extends Application {

    public static final String LOG_TAG = "GitHubApp";

    //TODO 5 Count app starts
    //TODO 6 Init stetho and verify count

    Preferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        mPreferences = new Preferences(this);
        Log.d(LOG_TAG, "App is started for " + mPreferences.getAppLaunches());
        mPreferences.incremetAppLaunches();

        // here you can od some global app initializations
        initLeakCannary();
        initStetho();
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
}