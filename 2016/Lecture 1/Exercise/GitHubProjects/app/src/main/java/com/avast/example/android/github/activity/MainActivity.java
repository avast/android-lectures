package com.avast.example.android.github.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avast.example.android.github.R;

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
                // TODO task 4
                // Show UserListActivity.
                // TODO task 5
                // Activity needs to be called different way.
            }
        });

        findViewById(R.id.btn_show_toast).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO task 7
                // Show EditText content in a Toast.
            }
        });

        findViewById(R.id.btn_get_detail).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO task 8
                // Download data about user with method GitHubApi.downloadUser() and parseUser() when ‘Show user detail
                // is clicked.
                // TODO task 10
                // Show name, repo URL and number of repos in TextViews on MainActivity.
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
                    // TODO task 6
                    // Show selected item in EditText
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO task 12
        // Make sure rotation works - selected user shouldn’t disappear after rotation.
    }

    @Override
    protected void onStop() {
        // TODO task 13
        // Make sure the selected user stays after you close the app - save to SharedPreferences
        super.onStop();
    }
}
