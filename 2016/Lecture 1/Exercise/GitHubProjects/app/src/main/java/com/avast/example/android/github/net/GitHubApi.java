package com.avast.example.android.github.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Nullable
    public static String downloadUserMock(String user) throws IOException {
        switch (user) {
            case "avast":
                return getAvastString();
            case "square":
                return getSquareString();
            case "inmite":
                return getInmiteString();
        }
        return null;
    }

    @NonNull
    public static User parseUser(@NonNull String response) throws JSONException {
        JSONObject responseObject = new JSONObject(response);
        String name = responseObject.getString("name");
        String company = responseObject.getString("html_url");
        int publicRepos = responseObject.getInt("public_repos");
        return new User(name, company, publicRepos);
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

    private static String getInmiteString() {
        return "{\"login\":\"inmite\",\"id\":3819616,\"avatar_url\":\"https://avatars.githubusercontent" +
            ".com/u/3819616?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/inmite\",\"html_url\":\"https://github.com/inmite\",\"followers_url\":\"https://api.github.com/users/inmite/followers\",\"following_url\":\"https://api.github.com/users/inmite/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/inmite/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/inmite/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/inmite/subscriptions\",\"organizations_url\":\"https://api.github.com/users/inmite/orgs\",\"repos_url\":\"https://api.github.com/users/inmite/repos\",\"events_url\":\"https://api.github.com/users/inmite/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/inmite/received_events\",\"type\":\"Organization\",\"site_admin\":false,\"name\":\"Inmite s.r.o.\",\"company\":null,\"blog\":\"http://www.inmite.eu/\",\"location\":\"Prague\",\"email\":null,\"hireable\":null,\"bio\":null,\"public_repos\":10,\"public_gists\":0,\"followers\":0,\"following\":0,\"created_at\":\"2013-03-09T20:37:18Z\",\"updated_at\":\"2016-02-27T07:03:38Z\"}";
    }

    private static String getSquareString() {
        return "{\"login\":\"square\",\"id\":82592,\"avatar_url\":\"https://avatars.githubusercontent" +
            ".com/u/82592?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/square\",\"html_url\":\"https://github.com/square\",\"followers_url\":\"https://api.github.com/users/square/followers\",\"following_url\":\"https://api.github.com/users/square/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/square/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/square/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/square/subscriptions\",\"organizations_url\":\"https://api.github.com/users/square/orgs\",\"repos_url\":\"https://api.github.com/users/square/repos\",\"events_url\":\"https://api.github.com/users/square/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/square/received_events\",\"type\":\"Organization\",\"site_admin\":false,\"name\":\"Square\",\"company\":null,\"blog\":\"http://square.github.io\",\"location\":null,\"email\":null,\"hireable\":null,\"bio\":null,\"public_repos\":178,\"public_gists\":5,\"followers\":0,\"following\":0,\"created_at\":\"2009-05-08T23:28:44Z\",\"updated_at\":\"2016-10-11T11:14:11Z\"}";
    }

    private static String getAvastString() {
        return "{\"login\":\"avast\",\"id\":3996079,\"avatar_url\":\"https://avatars.githubusercontent" +
            ".com/u/3996079?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/avast\",\"html_url\":\"https://github.com/avast\",\"followers_url\":\"https://api.github.com/users/avast/followers\",\"following_url\":\"https://api.github.com/users/avast/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/avast/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/avast/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/avast/subscriptions\",\"organizations_url\":\"https://api.github.com/users/avast/orgs\",\"repos_url\":\"https://api.github.com/users/avast/repos\",\"events_url\":\"https://api.github.com/users/avast/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/avast/received_events\",\"type\":\"Organization\",\"site_admin\":false,\"name\":\"Avast\",\"company\":null,\"blog\":\"http://www.avast.com\",\"location\":\"Czech Republic\",\"email\":null,\"hireable\":null,\"bio\":null,\"public_repos\":46,\"public_gists\":0,\"followers\":0,\"following\":0,\"created_at\":\"2013-03-28T13:17:06Z\",\"updated_at\":\"2016-01-19T19:52:30Z\"}";

    }
}
