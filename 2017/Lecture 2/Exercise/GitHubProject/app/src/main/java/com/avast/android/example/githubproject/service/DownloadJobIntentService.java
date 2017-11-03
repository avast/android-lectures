package com.avast.android.example.githubproject.service;

import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.avast.android.example.githubproject.DataStorage;
import com.avast.android.example.githubproject.github.ApiFactory;
import com.avast.android.example.githubproject.model.Repo;
import retrofit2.Call;

/**
 * Processing of background jobs that works correctly even on Android Oreo.
 */
public class DownloadJobIntentService extends JobIntentService {

  public static final int JOB_ID = 1000;

  @Override
  protected void onHandleWork(@NonNull Intent intent) {
    try {
      Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
      List<Repo> list = call.execute().body();
      DataStorage.storeData(System.currentTimeMillis(), "job-intent-service", "yes: " + list.size
          ());
    } catch (IOException e) {
      Log.e(DownloadJobIntentService.class.getSimpleName(), "Failed to get data");
    }
  }
}
