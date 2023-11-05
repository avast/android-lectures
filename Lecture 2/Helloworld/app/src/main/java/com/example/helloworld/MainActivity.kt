package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn_submit)
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)

        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        }

        button.setOnClickListener {onSubmitButtonClick() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(KEY_USERNAME, username.editText?.text.toString())
            putString(KEY_PASSWORD, password.editText?.text.toString())
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        username.editText?.setText(savedInstanceState.getString(KEY_USERNAME).orEmpty())
        password.editText?.setText(savedInstanceState.getString(KEY_PASSWORD).orEmpty())
    }

    private fun onSubmitButtonClick() {
        val intent = Intent(this, SecondaryActivity::class.java).apply {
            putExtra(SecondaryActivity.KEY_USERNAME, username.editText?.text.toString())
            putExtra(SecondaryActivity.KEY_PASSWORD, password.editText?.text.toString())
        }
        startActivity(intent)
    }

    private companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }
}