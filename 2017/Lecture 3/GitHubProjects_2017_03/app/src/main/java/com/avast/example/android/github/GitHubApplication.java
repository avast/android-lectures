package com.avast.example.android.github;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import com.avast.example.android.github.data.Settings;

/**
 * @author Tomáš Kypta (kypta@avast.com)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class GitHubApplication extends Application {

    public static final String LOG_TAG = "GitHubApp";

    Settings mSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = new Settings(this);
        mSettings.incrementAppLaunchesCount();

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