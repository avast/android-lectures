package com.avast.example.android.github.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.avast.example.android.github.activity.MainActivity;
import com.avast.example.android.github.api.GitHubApi;
import com.avast.example.android.github.api.ApiFactory;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.User;

/**
 * Simple and a bit stupid {@link Service} for downloading items from API.
 *
 * @author Tomáš Kypta (kypta)
 */
public class DownloadService extends Service {

    public static String EXTRA_USERNAME = "extra-username";

    private GitHubApi mGitHubApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mGitHubApi = ApiFactory.createGitHubApi();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        // TODO task 5 - download user data in a thread
        // TODO task 9 - notify about successful download

        return super.onStartCommand(intent, flags, startId);
    }


}
