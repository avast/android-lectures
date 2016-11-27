package com.avast.example.android.github.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.avast.example.android.github.fragment.UserDetailFragment;

/**
 * Simple and a bit stupid {@link Service} for downloading items from API.
 *
 * @author Tomáš Kypta (kypta)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class DownloadService extends Service {

    public static String EXTRA_USERNAME = "extra-username";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        // TODO task 4 - download user data in a thread
        // TODO task 7 - notify about successful download

        return super.onStartCommand(intent, flags, startId);
    }
}

