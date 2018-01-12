package com.avast.example.android.github.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * @author Lukas Prokop (prokop@avast.com)
 */
public interface GitHubApiRetrofit {

    String GITHUB_API_URL = "https://api.github.com";

    @GET("users/{user}")
    Call<User> user(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("repos/{owner}/{repoName}/contributors")
    Call<List<User>> repoContributors(@Path("owner") String owner, @Path("repoName") String repoName);
}
