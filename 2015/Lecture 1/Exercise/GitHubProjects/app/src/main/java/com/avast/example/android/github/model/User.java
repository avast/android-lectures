package com.avast.example.android.github.model;

import java.io.Serializable;

/**
 * POJO class for GitHub user.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 */
public class User implements Serializable {

    private String name;
    private int publicRepos;
    private String url;

    public User(String name, String url, int publicRepos) {
        this.name = name;
        this.publicRepos = publicRepos;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public String getUrl() {
        return url;
    }
}
