package com.avast.android.example.githubproject.service;

import java.io.IOException;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avast.android.example.githubproject.DataStorage;
import com.avast.android.example.githubproject.github.ApiFactory;
import com.avast.android.example.githubproject.model.Repo;
import retrofit2.Call;

/**
 * Example of IntentService.
 * The code is processed on a background thread.
 * Will not work correctly on Oreo.
 *
 * It's necessary to use JobIntentService for Oreo.
 */
public class DownloadIntentService extends IntentService {

  public static final String SERVICE_NAME = "DOWNLOAD";

  public DownloadIntentService() {
    super(SERVICE_NAME);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    // this code runs on a background thread
    try {
      Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
      List<Repo> list = call.execute().body();
      DataStorage.storeData(System.currentTimeMillis(), "intent-service", "yes: " + list.size());
    } catch (IOException e) {
      Log.e(DownloadIntentService.class.getSimpleName(), "Failed to get data");
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(DownloadIntentService.class.getSimpleName(), "IntentService destroyed");
  }
}
