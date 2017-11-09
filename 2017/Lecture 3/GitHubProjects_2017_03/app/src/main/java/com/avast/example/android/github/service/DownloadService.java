package com.avast.example.android.github.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.net.ApiFactory;

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
        Log.d(GitHubApplication.LOG_TAG, "onStartCommand");
        final String username = intent.getStringExtra(EXTRA_USERNAME);

        Call<User> call = ApiFactory.createGithubApi().getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                Storage.setUser(user);
                sendBroadcast(new Intent(UserDetailFragment.ACTION_USER_DOWNLOADED));
                stopSelf();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e(GitHubApplication.LOG_TAG, "Failed downloading of user", t);
                stopSelf();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}

