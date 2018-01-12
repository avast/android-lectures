package com.avast.example.android.github.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avast.example.android.github.R;
import com.avast.example.android.github.fragment.UserDetailFragment;
import com.avast.example.android.github.fragment.UserListFragment;

/**
 * Main screen displaying most of the stuff.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 *         Lukas Prokop (prokop@avast.com)
 */
public class MainActivity extends AppCompatActivity implements UserListFragment.OnUserSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder_user_list, new UserListFragment());
        ft.commit();
    }

    private void displayUserDetail(String username) {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder_user_detail, UserDetailFragment.newInstance(username));
            ft.addToBackStack(null);
            ft.commit();

        } else {
            Intent i = new Intent(this, UserDetailActivity.class);
            i.putExtra(UserDetailActivity.EXTRA_USERNAME, username);
            startActivity(i);
        }
    }

    @Override
    public void onUserSelected(String username) {
        displayUserDetail(username);
    }
}
