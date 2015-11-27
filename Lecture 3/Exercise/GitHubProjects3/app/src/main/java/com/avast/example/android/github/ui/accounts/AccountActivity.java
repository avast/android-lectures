package com.avast.example.android.github.ui.accounts;

import java.util.List;
import javax.inject.Inject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.avast.example.android.github.GitHubApplication;
import com.avast.example.android.github.R;
import com.avast.example.android.github.dagger.AppComponent;
import com.avast.example.android.github.db.AccountDataSource;
import com.avast.example.android.github.model.Account;
import com.avast.example.android.github.ui.repo.RepoActivity;
import com.avast.example.android.github.ui.repo.RepoFragment;

public class AccountActivity extends AppCompatActivity
    implements AccountContract,
    AddAccountDialogFragment.IAddAccountDialogListener {

    @Inject
    AccountDataSource mAccountDataSource;

    @Bind(R.id.coordinator)
    CoordinatorLayout vCoordinator;

    private AccountFragment mAccountFragment;
    private RepoFragment mRepoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GitHubApplication) getApplication()).getAppComponent().injectAccountActivity(this);

        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        mAccountDataSource.open();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mAccountFragment = (AccountFragment) fragmentManager.findFragmentById(R.id.fragment_account);
        mRepoFragment = (RepoFragment) fragmentManager.findFragmentById(R.id.fragment_repo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAccountDialogFragment.show(AccountActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // this might lead to some subtle bugs, the async task might be still touching the db
        // it should be handled by some manager elsewhere
        mAccountDataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // AccountContract

    @Override
    public void showAccount(Account account) {
        if (mRepoFragment != null) {
            mRepoFragment.showAccount(account);
        } else {
            Intent intent = new Intent(this, RepoActivity.class);
            intent.putExtra(RepoActivity.EXTRA_ACCOUNT, account);
            startActivity(intent);
        }
    }

    // AddAccountDialogFragment.IAddAccountDialogListener

    @Override
    public void onPositiveButtonClicked(String account) {
        boolean success = mAccountDataSource.addAccount(account);
        if (!success) {
            Snackbar.make(vCoordinator, R.string.db_addition_failed, Snackbar.LENGTH_LONG)
                .show();
        }
    }
}
