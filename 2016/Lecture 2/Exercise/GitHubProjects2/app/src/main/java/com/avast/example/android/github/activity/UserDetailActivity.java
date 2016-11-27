package com.avast.example.android.github.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.avast.example.android.github.R;
import com.avast.example.android.github.fragment.UserDetailFragment;

/**
 * Activity hosts {@link UserDetailFragment}.
 *
 * @author Lukas Prokop (prokop@avast.com)
 */
public class UserDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "username";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // TODO Task 3 - show up in action bar.
        // Show the Up button in the action bar.

        String username = getIntent().getStringExtra(EXTRA_USERNAME);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder_user_detail, UserDetailFragment.newInstance(username));
            ft.commit();
        }
    }
}
