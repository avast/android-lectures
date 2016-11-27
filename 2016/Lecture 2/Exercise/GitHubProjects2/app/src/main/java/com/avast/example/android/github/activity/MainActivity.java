package com.avast.example.android.github.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avast.example.android.github.R;

/**
 * Main screen displaying most of the stuff.
 *
 * @author David Vávra (vavra@avast.com)
 *         Tomáš Kypta (kypta@avast.com)
 *         Lukas Prokop (prokop@avast.com)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO Task 1 - add fragment
    }

    private void displayUserDetail(String username) {
        // TODO Task 2 - open activity/fragment
    }
}
