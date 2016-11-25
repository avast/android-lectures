package com.avast.example.android.github;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author Tomáš Kypta (kypta@avast.com)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class GitHubApplication extends Application {

    public static final String LOG_TAG = "GitHubApp";

    //TODO 5 Count app starts
    //TODO 6 Init stetho and verify count

    @Override
    public void onCreate() {
        super.onCreate();

        // here you can od some global app initializations
        initLeakCannary();
    }

    private void initLeakCannary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}