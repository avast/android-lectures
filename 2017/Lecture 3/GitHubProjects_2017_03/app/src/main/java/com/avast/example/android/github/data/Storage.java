package com.avast.example.android.github.data;

import java.util.List;

import android.util.Log;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * Simple static storage for downloaded data.
 * Awful thing, but we don't care right now.
 *
 * @author Tomáš Kypta
 */
public class Storage {

    private static User user;
    private static List<Repo> repos;
    private static List<User> contributors;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Log.d(GitHubApplication.LOG_TAG, user.getUrl());
        Storage.user = user;
    }

    public static List<Repo> getRepos() {
        return repos;
    }

    public static void setRepos(List<Repo> repos) {
        Storage.repos = repos;
    }

    public static List<User> getContributors() {
        return contributors;
    }

    public static void setContributors(List<User> contributors) {
        Storage.contributors = contributors;
    }
}
