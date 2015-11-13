package com.avast.example.android.github.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * @author Tomáš Kypta (kypta)
 */
public interface GitHubApi {

    @GET("/users/{username}")
    User getUser(@Path("username") String username);

    @GET("/users/{username}/repos")
    List<Repo> getUserRepos(@Path("username") String username);

    // TODO task 15
}
