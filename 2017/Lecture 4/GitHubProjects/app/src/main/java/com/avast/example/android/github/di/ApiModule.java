package com.avast.example.android.github.di;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.avast.example.android.github.net.GitHubApiRetrofit;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();
    }

    @Provides
    @Singleton
    public GitHubApiRetrofit provideGithubApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GitHubApiRetrofit.GITHUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

        return retrofit.create(GitHubApiRetrofit.class);
    }
}
