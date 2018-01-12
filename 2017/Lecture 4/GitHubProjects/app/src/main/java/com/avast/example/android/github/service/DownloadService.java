package com.avast.example.android.github.service;

import javax.inject.Inject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.avast.example.android.github.BaseApplication;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.net.GitHubApiRetrofit;

/**
 * Simple and a bit stupid {@link Service} for downloading items from API.
 *
 * @author Tomáš Kypta (kypta)
 * @author Lukas Prokop (prokop@avast.com)
 */
public class DownloadService extends Service {

    public static String EXTRA_USERNAME = "extra-username";

    @Inject
    GitHubApiRetrofit mGithubApi;

    @Override
    public void onCreate() {
        super.onCreate();
        ((BaseApplication) getApplication()).getComponent().inejct(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.d(BaseApplication.LOG_TAG, "onStartCommand");
        final String username = intent.getStringExtra(EXTRA_USERNAME);

        Call<User> user = mGithubApi.user(username);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Storage.setUser(response.body());
                sendBroadcast(new Intent(UserDetailFragment.ACTION_USER_DOWNLOADED));
                stopSelf();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}

