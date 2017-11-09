package com.avast.example.android.github.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String GITHUB_API_URL = "https://api.github.com";

    public static GitHubApi createGithubApi() {
        // create instance of github api using Retrofit library
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GitHubApi.class);
    }
}
