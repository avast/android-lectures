package com.avast.example.android.github.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.avast.example.android.github.R;

/**
 * A screen displaying a list of users.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 */
public class UserListActivity extends ListActivity {

    public static final String EXTRA_USER = "extra-user";

    private static final String[] items = {"avast", "inmite", "square"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.item, items);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
        String user = (String) getListAdapter().getItem(position);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER, user);
        setResult(RESULT_OK, intent);
        finish();
    }

}
