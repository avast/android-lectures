package com.avast.android.example.githubproject.github;

import java.util.List;

import com.avast.android.example.githubproject.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Part of GitHub API for Retrofit library (http://square.github.io/retrofit/).
 */
public interface GitHubApi {

  @Headers({
      "Accept: application/vnd.github.v3.full+json",
      "User-Agent: Retrofit-Sample-App"
  })
  @GET("/users/{username}/repos")
  Call<List<Repo>> getUserRepos(@Path("username") String username);
}