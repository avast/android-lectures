package com.avast.example.android.github.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.avast.example.android.github.R;

/**
 * A screen displaying a list of users.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 *         Lukas Prokop (prokop@avast.com)
 */
public class UserListActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra-user";

    private static final String[] items = {"avast", "inmite", "square"};

    private ListView vListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        vListView = findViewById(R.id.list_users);

        final ListAdapter adapter = new ArrayAdapter<>(this, R.layout.item_user_list, items);
        vListView.setAdapter(adapter);
        vListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO task 5
                // Go back and return result.
            }
        });
    }
}
