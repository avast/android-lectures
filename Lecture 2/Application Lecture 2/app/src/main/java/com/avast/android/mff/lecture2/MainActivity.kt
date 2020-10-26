package com.avast.android.mff.lecture2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private val txtUsername: TextInputLayout by lazy {
        findViewById(R.id.txt_username)
    }

    private val txtPassword: TextInputLayout by lazy {
        findViewById(R.id.txt_password)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState != null) {
            txtUsername.editText?.setText(savedInstanceState.getString(KEY_USERNAME))
            txtPassword.editText?.setText(savedInstanceState.getString(KEY_PASSWORD))
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        findViewById<Button>(R.id.btn_open_secondary_activity).setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            val extras = bundleOf(
                SecondaryActivity.KEY_USERNAME to txtUsername.editText?.text.toString(),
                SecondaryActivity.KEY_PASSWORD to txtPassword.editText?.text.toString(),
            )
            intent.putExtras(extras)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, txtUsername.editText?.text.toString())
        outState.putString(KEY_PASSWORD, txtPassword.editText?.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }
}