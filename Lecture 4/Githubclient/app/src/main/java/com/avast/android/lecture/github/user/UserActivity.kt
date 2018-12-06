package com.avast.android.lecture.github.user

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.avast.android.lecture.github.R

/**
 * Activity holding [UserFragment] with user detail.
 */
class UserActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val username = intent.getStringExtra(UserFragment.KEY_USERNAME)
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, UserFragment.newInstance(username))
                .commit()

        Log.d("User activity", username)

        setupNavigation()
    }

    /**
     * Reaction to the menu item click. In this case it is used just for "UP" navigation.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    /**
     * Show navigation up button
     */
    private fun setupNavigation() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}