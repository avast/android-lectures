package com.avast.example.android.github.dagger.module;

import java.util.Collections;
import java.util.List;

import android.content.Context;

import retrofit.Callback;
import retrofit.http.Path;

import com.avast.example.android.github.api.GitHubApi;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * @author Tomáš Kypta (kypta)
 */
public class DevAppModule extends AppModule {

    public DevAppModule(Context applicationContext) {
        super(applicationContext);
    }

    @Override
    protected GitHubApi provideGitHubApi() {
        return new GitHubApi() {
            @Override
            public User getUser(@Path("username") String username) {
                User user = new User();
                user.setName("avast");
                user.setUrl("https://github.com/avast");
                user.setPublic_repos(20);
                return user;
            }

            @Override
            public List<Repo> getUserRepos(@Path("username") String username) {
                return Collections.EMPTY_LIST;
            }

            @Override
            public void getUserRepos(@Path("username") String username, Callback<List<Repo>> callback) {
                callback.success(getUserRepos(username), null);
            }
        };
    }
}
