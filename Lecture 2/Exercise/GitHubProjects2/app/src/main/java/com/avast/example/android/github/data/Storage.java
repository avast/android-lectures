package com.avast.example.android.github.data;

import java.util.List;

import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * Simple static storage for downloaded data.
 *
 * @author Tomáš Kypta
 */
public class Storage {

    private static User user;
    private static List<Repo> repos;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Storage.user = user;
    }

    public static List<Repo> getRepos() {
        return repos;
    }

    public static void setRepos(List<Repo> repos) {
        Storage.repos = repos;
    }
}
