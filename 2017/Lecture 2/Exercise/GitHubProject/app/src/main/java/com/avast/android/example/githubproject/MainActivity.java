package com.avast.android.example.githubproject;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avast.android.example.githubproject.github.ApiFactory;
import com.avast.android.example.githubproject.model.Repo;
import com.avast.android.example.githubproject.service.DownloadIntentService;
import com.avast.android.example.githubproject.service.DownloadJobIntentService;
import com.avast.android.example.githubproject.service.DownloadService;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

  private TextView txtData;
  private Handler mHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    txtData = (TextView) findViewById(R.id.txt_data);
    // handler will process data on the main thread
    mHandler = new Handler(Looper.getMainLooper());

    MyAsyncTask.setCallback(mCallbackImplementation);

    findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO task - start download service
        startService(new Intent(MainActivity.this, DownloadService.class));
      }
    });

    findViewById(R.id.btn_stop_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO task - stop download service
        stopService(new Intent(MainActivity.this, DownloadService.class));
      }
    });

    findViewById(R.id.btn_intent_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO task - start intent service
        startService(new Intent(MainActivity.this, DownloadIntentService.class));
      }
    });

    findViewById(R.id.btn_job_intent_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO tasl - use DownloadJobIntentService
        DownloadJobIntentService.enqueueWork(MainActivity.this, DownloadJobIntentService.class,
            DownloadJobIntentService.JOB_ID, new Intent());
      }
    });
  }

  public void download(View view) {
    MyAsyncTask.startTheTask(mCallbackImplementation);
  }

  /*
   * Demonstration of incorrect usage of downloading with Thread.
   * The thread holds implicit reference to MainActivity instance and leaks it.
   *
   * Besides the problem with memory leak there's a potential for app crash.
   */
  public void downloadWithThread(View view) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        //         do the download here
        try {
          Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
          final List<Repo> list = call.execute().body();
          DataStorage.storeData(System.currentTimeMillis(), "activity", "yes: " + list.size());
          Thread.sleep(2000);
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              txtData.setText("downloaded data: " + list.size());
            }
          });
        } catch (Exception e) {
          Log.e(MainActivity.class.getSimpleName(), "Failed to get data");
        }
      }
    }).start();
  }


  /*
   * Demonstration of incorrect usage of downloading with AsyncTask.
   * The AsyncTask holds implicit reference to MainActivity instance and leaks it.
   *
   * Besides the problem with memory leak there's a potential for app crash.
   */
  public void downloadWithAsyncTask(View view) {
    new AsyncTask<Void, Void, String>() {
      // problematic AsyncTask - hold reference to MainActivity instance and leaks it
      @Override
      protected String doInBackground(Void... voids) {
        // this code runs on a background thread
        try {
          Call<List<Repo>> call = ApiFactory.createGitHubApi().getUserRepos("avast");
          final List<Repo> list = call.execute().body();
          DataStorage.storeData(System.currentTimeMillis(), "asyncTask", "yes: " + list.size());
          Thread.sleep(5000);
          return "download data with asynctask: " + list.size();
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }

      @Override
      protected void onPostExecute(String s) {
        txtData.setText(s);
      }
    }.execute();
  }


  /*
   * Demonstration of proper usage of AsyncTask.
   * The task isn't non-static inner class, so it doesn't have implicit reference to MainActivity
   * instance.
   */
  public void downloadWithProperAsyncTask(View view) {
    new MyAsyncTask(mCallbackImplementation).execute();
  }

  /**
   * A callback interface for passing results back to the activity.
   */
  public interface Callback {
    void onDataAvailable(String s);
  }

  /**
   * Implementation of the callback.
   * Warning: This implementation is anonymous inner class and it has implicit reference to the
   * activity. It's important that an AsyncTask/a Thread doesn't use strong reference to
   * store it. Using strong reference is risky, it can cause memory leaks (and crashes when
   * accessing invalid activity).
   * It's important to use {@link WeakReference} to hold it.
   */
  private Callback mCallbackImplementation = new Callback() {
    @Override
    public void onDataAvailable(String s) {
      txtData.setText(s);
    }
  };

}
