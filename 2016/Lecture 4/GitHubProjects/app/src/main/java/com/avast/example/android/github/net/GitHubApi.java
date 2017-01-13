package com.avast.example.android.github.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;

/**
 * Contains methods for downloading stuff from GitHub API and parsing them to Java objects.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 *         Lukas Prokop (prokop@avast.com)
 */
public class GitHubApi {

    private static final String GITHUB_API_URL = "https://api.github.com";

    @Nullable
    public static String downloadUser(String user) throws IOException {
        return downloadFromUrl(GITHUB_API_URL + "/users/" + user);
    }

    public static String downloadUserRepos(String username) throws IOException {
        return downloadFromUrl(GITHUB_API_URL + "/users/" + username + "/repos");
    }

    public static String downloadRepoContributors(String owner, String repoName) throws IOException {
        return downloadFromUrl(GITHUB_API_URL + "/repos/" + owner + "/" + repoName + "/contributors");
    }

    @NonNull
    public static User parseUser(@NonNull String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        String name = responseObject.has("name") ? responseObject.getString("name") : "";
        String company = responseObject.has("html_url") ? responseObject.getString("html_url") : "";
        int publicRepos = responseObject.has("public_repos") ? responseObject.getInt("public_repos") : 0;
        return new User(name, company, publicRepos);
    }

    @NonNull
    public static Repo parseRepo(@NonNull String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        String name = responseObject.getString("name");
        String description = responseObject.getString("description");
        String url = responseObject.getString("url");
        long id = responseObject.getLong("id");
        return new Repo(id, name, description, url);
    }

    public static List<Repo> parseListRepo(@NonNull String response) throws JSONException {
        JSONArray responseArray = new JSONArray(response);

        List<Repo> reposList = new ArrayList<>(responseArray.length());
        for (int i = 0; i < responseArray.length(); i++) {
            reposList.add(parseRepo(responseArray.getJSONObject(i).toString()));
        }

        return reposList;
    }

    public static List<User> parseContributors(@NonNull String response) throws JSONException {
        JSONArray responseArray = new JSONArray(response);

        List<User> contributorsList = new ArrayList<>(responseArray.length());
        for (int i = 0; i < responseArray.length(); i++) {
            contributorsList.add(parseUser(responseArray.getJSONObject(i).toString()));
        }

        return contributorsList;
    }

    @Nullable
    private static String downloadFromUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if (urlConnection.getResponseCode() == 200) {
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            is.close();
            return sb.toString();
        }
        return null;
    }
}
