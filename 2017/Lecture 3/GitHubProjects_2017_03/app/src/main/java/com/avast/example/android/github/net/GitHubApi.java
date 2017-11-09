package com.avast.example.android.github.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * Created by lukas on 11/8/17.
 */

public interface GitHubApi {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}/repos")
    Call<List<Repo>> getUserRepo(@Path("username") String username);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/repos/{owner}/{repository}/contributors")
    Call<List<User>> getRepoContributors(@Path("owner") String owner,
            @Path("repository") String repoName);


}
