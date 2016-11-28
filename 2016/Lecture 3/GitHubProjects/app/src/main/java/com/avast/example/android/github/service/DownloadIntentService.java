package com.avast.example.android.github.service;

import java.io.IOException;
import java.util.List;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.activity.ReposListActivity;
import com.avast.example.android.github.activity.UserDetailActivity;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.net.GitHubApi;

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
            String response = GitHubApi.downloadUserRepos(username);
            List<Repo> repoList = GitHubApi.parseListRepo(response);
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

            // TODO 1 show notification when list of repository is downloaded
            Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(String.format("Repos for \"%s\" downloaded", username))
                .setContentTitle("Repos downloaded")
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .build();

            NotificationManagerCompat.from(this).notify(0, notification);



            // TODO 2 open activity with repository list when user tap on notification
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
