package com.avast.example.android.github.di;

import javax.inject.Singleton;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideAppContext() {
        return mContext;
    }
}
