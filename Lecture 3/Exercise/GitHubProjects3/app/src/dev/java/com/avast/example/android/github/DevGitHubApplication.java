package com.avast.example.android.github;

import com.avast.example.android.github.dagger.AppComponent;
import com.avast.example.android.github.dagger.DaggerAppComponent;
import com.avast.example.android.github.dagger.module.DevAppModule;

/**
 * @author Tomáš Kypta (kypta)
 */
public class DevGitHubApplication extends GitHubApplication {

    @Override
    protected AppComponent initAppComponent() {
        // TODO task 16
        return null;
    }
}
