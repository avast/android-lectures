package com.avast.example.android.github.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lukas on 11/10/17.
 */

public class Settings {

    private static final String KEY_APP_LAUNCHES = "app_launch";

    private final SharedPreferences mSharedPreferences;

    public Settings(Context context) {
        mSharedPreferences = context.getSharedPreferences("prefs.xml", Context.MODE_PRIVATE);
    }

    public void incrementAppLaunchesCount() {
        mSharedPreferences.edit().putInt(KEY_APP_LAUNCHES, getAppLaunchesCount() + 1).apply();
    }

    public int getAppLaunchesCount() {
        return mSharedPreferences.getInt(KEY_APP_LAUNCHES, 0);
    }
}
