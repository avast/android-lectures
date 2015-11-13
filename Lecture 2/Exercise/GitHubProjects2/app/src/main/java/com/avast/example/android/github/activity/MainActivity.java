package com.avast.example.android.github.activity;

import java.util.List;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avast.example.android.github.R;
import com.avast.example.android.github.data.Storage;
import com.avast.example.android.github.model.Repo;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.service.DownloadIntentService;
import com.avast.example.android.github.service.DownloadService;

/**
 * Main screen displaying most of the stuff.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 */
public class MainActivity extends Activity {

    private static final int REQUEST_CODE_USER = 1;
    public static final String ACTION_USER_DOWNLOADED = "action-user-downloaded";
    public static final String ACTION_REPOS_DOWNLOADED = "action-repos-downloaded";

    private EditText vEtUser;
    private TextView vTxtName;
    private TextView vTxtUrl;
    private TextView vTxtPublicRepos;


    // TODO task 8 - create broadcast receiver for being notified about download of user data
    private BroadcastReceiver mUsernameReceiver;

    // TODO task 10 - create broadcast receiver for being notified about download of repos data
    private BroadcastReceiver mReposReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vEtUser = (EditText) findViewById(R.id.et_user);
        vTxtName = (TextView) findViewById(R.id.txt_name);
        vTxtUrl = (TextView) findViewById(R.id.txt_url);
        vTxtPublicRepos = (TextView) findViewById(R.id.txt_public_repos);

        findViewById(R.id.btn_get_user).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_USER);
            }
        });

        findViewById(R.id.btn_get_detail).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO task 4 - start service for downloading user
            }
        });

        findViewById(R.id.btn_get_repos).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO task 7 - start IntentService for downloading repositories
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_USER:
                if (resultCode == RESULT_OK) {
                    vEtUser.setText(data.getStringExtra(UserListActivity.EXTRA_USER));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // TODO task 8
        // TODO task 10
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO task 8
        // TODO task 10
    }

    private void displayUser(User user) {
        vTxtName.setText(user.getName());
        vTxtPublicRepos.setText(String.valueOf(user.getPublic_repos()));
        vTxtUrl.setText(user.getUrl());
    }

    // TODO task 12 - show notification
    private void showReposNotification() {
        List<Repo> repos = Storage.getRepos();

        // TODO task 13 - display ReposActivity as an action to click

        // TODO task 14

    }
}
