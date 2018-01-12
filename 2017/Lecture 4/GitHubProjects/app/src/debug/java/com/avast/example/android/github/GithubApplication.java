package com.avast.example.android.github;

import com.facebook.stetho.Stetho;

/**
 * Created by lukas on 11/23/17.
 */
public class GithubApplication extends BaseApplication {

    @Override
    protected void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

}
