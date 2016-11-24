package com.avast.example.android.github.fragment;

import java.io.IOException;
import java.util.Locale;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.net.GitHubApi;
import com.avast.example.android.github.service.DownloadIntentService;
import com.avast.example.android.github.service.DownloadService;

/**
 * Fragment with user detail.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserDetailFragment extends Fragment {

    public static final String EXTRA_USERNAME = "username";

    public static final String ACTION_USER_DOWNLOADED = "action-user-downloaded";
    public static final String ACTION_REPOS_DOWNLOADED = "action-repos-downloaded";

    private TextView vUsername;
    private TextView vReposCount;
    private TextView vWebAddress;
    private Button vBtnOpenWeb;
    private Button vBtnDownloadUser;
    private Button vBtnDownloadRepos;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
        }
    };

    private BroadcastReceiver mReposReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Repos list downloaded", Toast.LENGTH_SHORT).show();
        }
    };

    public static UserDetailFragment newInstance(String username) {
        UserDetailFragment userDetailFragment = new UserDetailFragment();
        Bundle data = new Bundle();
        data.putString(EXTRA_USERNAME, username);

        userDetailFragment.setArguments(data);
        return userDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        vUsername = (TextView) view.findViewById(R.id.txt_name);
        vReposCount = (TextView) view.findViewById(R.id.txt_public_repos);
        vWebAddress = (TextView) view.findViewById(R.id.txt_url);
        vBtnOpenWeb = (Button) view.findViewById(R.id.btn_open_web);
        vBtnDownloadUser = (Button) view.findViewById(R.id.btn_download_user);
        vBtnDownloadRepos = (Button) view.findViewById(R.id.btn_download_repos);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_USER_DOWNLOADED);
        getActivity().registerReceiver(mReceiver, intentFilter);
        // TODO Task 6
        // TODO Task 8

        IntentFilter reposFilter = new IntentFilter();
        reposFilter.addAction(ACTION_REPOS_DOWNLOADED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReposReceiver, reposFilter);
    }

    @Override
    public void onStop() {
        // TODO Task 6
        getActivity().unregisterReceiver(mReceiver);
        // TODO Task 8
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReposReceiver);
        super.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String username = getArguments().getString(EXTRA_USERNAME);

        vBtnDownloadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Task 4 - start download service
                Intent i = new Intent(getActivity(), DownloadService.class);
                i.putExtra(DownloadService.EXTRA_USERNAME, username);
                getActivity().startService(i);
            }
        });

        vBtnDownloadRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Task 5 - start download intent service
                Intent i = new Intent(getActivity(), DownloadIntentService.class);
                i.putExtra(DownloadIntentService.EXTRA_USERNAME, username);
                getActivity().startService(i);
            }
        });

        try {
            String response = GitHubApi.downloadUserMock(username);
            if (TextUtils.isEmpty(response)) {
                return;
            }

            final User user = GitHubApi.parseUser(response);
            vUsername.setText(user.getName());
            vReposCount.setText(String.format(Locale.US, "%d", user.getPublicRepos()));
            vWebAddress.setText(user.getUrl());

            vBtnOpenWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBrowser(user.getUrl());
                }
            });
        } catch (JSONException e) {
            Log.e(GitHubApplication.LOG_TAG, "Json parsing failed", e);
        } catch (IOException e) {
            Log.e(GitHubApplication.LOG_TAG, "Downloading failed", e);
        }
    }

    private void openBrowser(String url) {
        Intent i = new Intent();
        i.setData(Uri.parse(url));
        i.setAction(Intent.ACTION_VIEW);
        startActivity(i);
    }
}
