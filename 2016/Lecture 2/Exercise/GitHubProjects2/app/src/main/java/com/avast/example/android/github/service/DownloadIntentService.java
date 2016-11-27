package com.avast.example.android.github.service;

import android.app.IntentService;
import android.content.Intent;

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
        // TODO task 5 - download list of repositories

        // TODO task 9 - use LocalBroadcastManager to synchronously notify about successful download
    }
}
