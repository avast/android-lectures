package com.avast.android.mff.lecture2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondaryActivity : AppCompatActivity() {

    private val txtUsername: TextView by lazy {
        findViewById(R.id.txt_username_value)
    }
    private val txtPassword: TextView by lazy {
        findViewById(R.id.txt_password_value)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)
        setSupportActionBar(findViewById(R.id.toolbar))

        intent.extras?.let {extras ->
            txtUsername.text = extras.getString(KEY_USERNAME)
            txtPassword.text = extras.getString(KEY_PASSWORD)
        }
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }
}