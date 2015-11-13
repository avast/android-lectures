package com.avast.example.android.github.service;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.avast.example.android.github.activity.MainActivity;
import com.avast.example.android.github.api.ApiFactory;
import com.avast.example.android.github.api.GitHubApi;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.Repo;

/**
 * An {@link IntentService} for demonstration.
 *
 * @author Tomáš Kypta (kypta)
 */
public class DownloadIntentService extends IntentService {

    public static String EXTRA_USERNAME = "extra-username";
    private GitHubApi mGitHubApi;

    public DownloadIntentService() {
        super("Download");
        mGitHubApi = ApiFactory.createGitHubApi();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO task 7 - download list of repositories

        // TODO task 11 - use LocalBroadcastManager to synchronously notify about successful download
    }
}
