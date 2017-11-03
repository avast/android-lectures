package com.avast.android.example.githubproject;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.avast.android.example.githubproject.github.ApiFactory;
import com.avast.android.example.githubproject.model.Repo;
import retrofit2.Call;

/**
 * An AsyncTask that doesn't leak {@link MainActivity}.
 * It uses {@link WeakReference} to avoid memory leaks.
 *
 */
class MyAsyncTask extends AsyncTask<Void, Void, String> {

  public static MyAsyncTask sTheTask;
  /**
   * Weak reference that holds the callback.
   * This will avoid memory leak.
   */
  private WeakReference<MainActivity.Callback> mCallback;

  public MyAsyncTask(MainActivity.Callback callback) {
    this.mCallback = new WeakReference<MainActivity.Callback>(callback);
  }

  /*
   * Set new callback that will receive the result.
   */
  public static void setCallback(MainActivity.Callback callback) {
    if (sTheTask != null) {
      sTheTask.mCallback = new WeakReference<MainActivity.Callback>(callback);
    }
  }

  /*
   * Start the AsyncTask. If there is another instance of this class running, stop it and create
   * new instance.
   * Only one instance can run at a time.
   */
  public static void startTheTask(MainActivity.Callback callback) {
    if (sTheTask != null) {
      sTheTask.cancel(true);
      sTheTask = null;
    }
    sTheTask = new MyAsyncTask(callback);
    sTheTask.execute();
  }


  @Override
  protected String doInBackground(Void... voids) {
    // this runs on a background thread
    try {
      Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
      final List<Repo> list = call.execute().body();
      DataStorage.storeData(System.currentTimeMillis(), "asyncTask", "yes: " + list.size());
      Thread.sleep(3000);
      return "download data with asynctask: " + list.size();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected void onPostExecute(String s) {
    // this runs on the main thread
    MainActivity.Callback callback = mCallback.get();
    if (callback != null) {
      callback.onDataAvailable(s);
    } else {
      // The callback can be null, no result is passed to any callback.
      // The data is lost here.
      // It's better to store the data somewhere.
      Log.d("asynctask", "weak reference is null");
    }
  }
}
