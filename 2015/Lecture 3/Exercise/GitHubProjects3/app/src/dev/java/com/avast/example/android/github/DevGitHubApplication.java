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
        return DaggerAppComponent.builder()
            .appModule(new DevAppModule(this))
            .build();
    }
}
