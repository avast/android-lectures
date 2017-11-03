package com.avast.android.example.githubproject.github;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

  private static final String GITHUB_API_URL = "https://api.github.com";

  public static GitHubApi createGitHubApi() {
    // create instance of GitHubAPI using Retrofit library
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(GITHUB_API_URL) // base URL of the REST API
        .addConverterFactory(GsonConverterFactory.create()) // convert data from Json
        .build();

    return retrofit.create(GitHubApi.class);
  }
}
