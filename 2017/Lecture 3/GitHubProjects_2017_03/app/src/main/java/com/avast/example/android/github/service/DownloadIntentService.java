package com.avast.example.android.github.service;

import java.io.IOException;
import java.util.List;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.activity.ReposListActivity;
import com.avast.example.android.github.activity.UserDetailActivity;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.net.ApiFactory;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
public class DownloadIntentService extends IntentService {

    public static String EXTRA_USERNAME = "extra-username";

    public DownloadIntentService() {
        super("Download");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(GitHubApplication.LOG_TAG, "DownloadIntentService onHandleIntent");

        try {
            final String username = intent.getStringExtra(EXTRA_USERNAME);
            List<Repo> repoList = ApiFactory.createGithubApi().getUserRepo(username).execute().body();
            Storage.setRepos(repoList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UserDetailFragment.ACTION_REPOS_DOWNLOADED));

            Intent resultIntent = new Intent(this, ReposListActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            stackBuilder.addNextIntentWithParentStack(resultIntent);
            Intent i = stackBuilder.editIntentAt(1);
            i.putExtra(UserDetailActivity.EXTRA_USERNAME, username);

            // Gets a PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, "channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notification title")
                    .setContentText("Notification content")
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(1, notification);

            // TODO 2 open activity with repository list when user tap on notification
        } catch (IOException e) {
            Log.e(GitHubApplication.LOG_TAG, "IO error during loading of users", e);
        }
    }
}
