package com.avast.example.android.github.activity;

import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import com.avast.example.android.github.R;
import com.avast.example.android.github.model.User;
import com.avast.example.android.github.net.GitHubApi;

/**
 * Main screen displaying most of the stuff.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 *         Lukas Prokop (prokop@avast.com)
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_USER = 1;

    private EditText vEtUser;
    private TextView vTxtName;
    private TextView vTxtUrl;
    private TextView vTxtPublicRepos;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vEtUser = (EditText) findViewById(R.id.et_user);
        vTxtName = (TextView) findViewById(R.id.txt_name);
        vTxtUrl = (TextView) findViewById(R.id.txt_url);
        vTxtPublicRepos = (TextView) findViewById(R.id.txt_public_repos);

        if (savedInstanceState != null) {
            mUser = (User) savedInstanceState.getSerializable(UserListActivity.EXTRA_USER);
            displayUserDetail();
        }

        findViewById(R.id.btn_get_user).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);

                startActivityForResult(intent, REQUEST_CODE_USER);
                // TODO task 5
                // Activity needs to be called different way.
            }
        });

        findViewById(R.id.btn_show_toast).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = vEtUser.getText().toString();
                Toast.makeText(MainActivity.this, user, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn_get_detail).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String userData = GitHubApi.downloadUserMock(vEtUser.getText().toString());
                    mUser = GitHubApi.parseUser(userData);

                    displayUserDetail();
                } catch (IOException e) {
                    Log.e("TAG", "Something bad happend during downloading user", e);
                } catch (JSONException e) {
                    Log.e("TAG", "Something bad happend during parsing user", e);
                }

                // TODO task 8
                // Download data about user with method GitHubApi.downloadUser() and parseUser() when ‘Show user detail
                // is clicked.
                // TODO task 10
                // Show name, repo URL and number of repos in TextViews on MainActivity.
            }
        });

        findViewById(R.id.btn_open_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mUser.getUrl()));
                startActivity(i);

            }
        });

        // TODO task 11
        // Add button 'Open web' which opens URL of the repo in the browser.
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UserListActivity.EXTRA_USER, mUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    void displayUserDetail() {
        if (mUser != null) {
            vTxtName.setText(mUser.getName());
            vTxtUrl.setText(mUser.getUrl());
            vTxtPublicRepos.setText(Integer.toString(mUser.getPublicRepos()));
        }
    }
}
