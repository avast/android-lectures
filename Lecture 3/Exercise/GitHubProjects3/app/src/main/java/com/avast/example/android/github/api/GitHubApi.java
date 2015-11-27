package com.avast.example.android.github.api;

import java.util.List;

import retrofit.Callback;
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

    /**
     * This is asynchronous call.
     * Note that this is API in v1.9.0, latest beta versions of v2.0.0 have different API.
     * @param username
     */
    @GET("/users/{username}/repos")
    void getUserRepos(@Path("username") String username, Callback<List<Repo>> callback);


}
