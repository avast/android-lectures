package com.avast.android.example.githubproject;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Trivial in-memory storage.
 * Erased when the system decides to kill the app.
 *
 */
public class DataStorage {

  private static class Data {
    private long millis;
    private String means;
    private String data;

    public Data(long millis, String means, String data) {
      this.millis = millis;
      this.means = means;
      this.data = data;
    }

    public long getMillis() {
      return millis;
    }

    public String getMeans() {
      return means;
    }

    public String getData() {
      return data;
    }
  }

  private static List<Data> list = new ArrayList<>();

  public static void storeData(long millis, String means, String data) {
    list.add(new Data(millis, means, data));
    Log.i("data-store", means + " " + data);
  }
}
