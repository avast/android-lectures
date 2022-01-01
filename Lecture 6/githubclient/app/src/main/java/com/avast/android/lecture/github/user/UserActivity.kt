package com.avast.android.lecture.github.user

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.avast.android.lecture.github.R

/**
 * Activity holding [UserFragment] with user detail.
 */
class UserActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val username = intent.getStringExtra(UserFragment.KEY_USERNAME).orEmpty()

        Log.d(this.javaClass.simpleName, "User activity, user: $username")
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, UserFragment.newInstance(username)).commit()

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