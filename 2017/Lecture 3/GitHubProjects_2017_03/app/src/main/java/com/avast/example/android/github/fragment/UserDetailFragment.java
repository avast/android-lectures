package com.avast.example.android.github.fragment;

import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.example.android.github.R;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.User;
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
    private Button vBtnDownloadRepos;

    String mUsername;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showUserDetail();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsername = getArguments().getString(EXTRA_USERNAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        vUsername = view.findViewById(R.id.txt_name);
        vReposCount = view.findViewById(R.id.txt_public_repos);
        vWebAddress = view.findViewById(R.id.txt_url);
        vBtnDownloadRepos = view.findViewById(R.id.btn_download_repos);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vBtnDownloadRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DownloadIntentService.class);
                i.putExtra(DownloadIntentService.EXTRA_USERNAME, mUsername);
                getActivity().startService(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent i = new Intent(getActivity(), DownloadService.class);
        i.putExtra(DownloadService.EXTRA_USERNAME, mUsername);
        getActivity().startService(i);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_USER_DOWNLOADED);
        getActivity().registerReceiver(mReceiver, intentFilter);

        IntentFilter reposFilter = new IntentFilter();
        reposFilter.addAction(ACTION_REPOS_DOWNLOADED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReposReceiver, reposFilter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReposReceiver);
        super.onStop();
    }

    private void showUserDetail() {
        final User user = Storage.getUser();

        vUsername.setText(user.getName());
        vReposCount.setText(String.format(Locale.US, "%d", user.getPublicRepos()));
        vWebAddress.setText(user.getUrl());
    }
}
