package com.avast.example.android.github.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.avast.example.android.github.model.User;

/**
 * Contains methods for downloading stuff from GitHub API and parsing them to Java objects.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 */
public class GitHubApi {

    private static final String GITHUB_API_URL = "https://api.github.com";

    public static String downloadUser(String user) throws IOException {
        return downloadFromUrl(GITHUB_API_URL + "/users/" + user);
    }

    public static User parseUser(String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        String name = responseObject.getString("name");
        String company = responseObject.getString("html_url");
        int publicRepos = responseObject.getInt("public_repos");
        return new User(name, company, publicRepos);
    }

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
