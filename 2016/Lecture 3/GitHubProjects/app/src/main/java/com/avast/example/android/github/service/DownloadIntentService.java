package com.avast.example.android.github.service;

import java.io.IOException;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;

import com.avast.example.android.github.GitHubApplication;
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

            // TODO 1 show notification when list of repository is downloaded


            // TODO 2 open activity with repository list when user tap on notification
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
