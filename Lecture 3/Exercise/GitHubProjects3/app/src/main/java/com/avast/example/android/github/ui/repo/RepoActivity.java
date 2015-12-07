package com.avast.example.android.github.ui.repo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.avast.example.android.github.R;
import com.avast.example.android.github.model.Account;

/**
 * @author Tomáš Kypta (kypta)
 */
public class RepoActivity extends AppCompatActivity {

    private RepoFragment mRepoFragment;
    public static String EXTRA_ACCOUNT = "extra-account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mRepoFragment = (RepoFragment) fragmentManager.findFragmentById(R.id.fragment_repo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Account account = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT);
        mRepoFragment.showAccount(account);
    }
}
