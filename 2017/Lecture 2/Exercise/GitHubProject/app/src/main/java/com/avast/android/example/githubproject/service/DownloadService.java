package com.avast.android.example.githubproject.service;

import java.io.IOException;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avast.android.example.githubproject.DataStorage;
import com.avast.android.example.githubproject.github.ApiFactory;
import com.avast.android.example.githubproject.model.Repo;
import retrofit2.Call;

/**
 * Example service.
 * It is a background service. It will not work correctly on Oreo.
 *
 * For Oreo use foreground service (see the docs) or JobIntentService.
 */
public class DownloadService extends Service {

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // this run on the main thread
    handleIntent(intent);
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(DownloadService.class.getSimpleName(), "Download service onDestroy()");
  }

  private void handleIntent(Intent intent) {
    // we are on the main thread here
    // we have to do all the time-consuming work and IO operation on a background thread
    new Thread(new Runnable() {
      @Override
      public void run() {
//         do the download here
        try {
          Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
          List<Repo> list = call.execute().body();
          DataStorage.storeData(System.currentTimeMillis(), "service", "yes: " + list.size());
        } catch (IOException e) {
          Log.e(DownloadService.class.getSimpleName(), "Failed to get data");
        }
        stopSelf();
      }
    }).start();
  }
}
