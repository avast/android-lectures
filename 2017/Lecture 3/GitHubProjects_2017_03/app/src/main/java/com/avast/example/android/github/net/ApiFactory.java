package com.avast.example.android.github.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String GITHUB_API_URL = "https://api.github.com";

    public static GitHubApi createGithubApi() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        // create instance of github api using Retrofit library
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(GITHUB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GitHubApi.class);
    }
}
