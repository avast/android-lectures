package com.avast.example.android.github.service;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.activity.MainActivity;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.net.GitHubApi;

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
        // TODO task 4 - download user data in a thread
        final String username = intent.getStringExtra(EXTRA_USERNAME);

        new AsyncTask<String, Void, User>() {

            @Override
            protected User doInBackground(String... params) {
                try {
                    String response = GitHubApi.downloadUser(username);
                    User user = GitHubApi.parseUser(response);
                    return user;
                } catch (IOException e) {
                    Log.e(GitHubApplication.LOG_TAG, "failed download data", e);
                } catch (JSONException e) {
                    Log.e(GitHubApplication.LOG_TAG, "failed parse data", e);
                }

                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                Storage.setUser(user);
                sendBroadcast(new Intent(UserDetailFragment.ACTION_USER_DOWNLOADED));
                stopSelf();
            }
        }.execute(username);


        // TODO task 7 - notify about successful download

        return super.onStartCommand(intent, flags, startId);
    }
}

