package com.avast.mff.lecture2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button
    lateinit var etUsername: TextInputLayout
    lateinit var etPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Manual view binding
        btnSubmit = findViewById(R.id.btn_submit)
        etUsername = findViewById(R.id.txt_username)
        etPassword = findViewById(R.id.txt_password)

        /**
         * Restore the UI state after configuration change.
         */
        savedInstanceState?.let {
            etUsername.editText?.setText(it.getString(KEY_USERNAME, ""))
            etPassword.editText?.setText(it.getString(KEY_PASS, ""))
        }

        btnSubmit.setOnClickListener {
            Toast.makeText(this, R.string.txt_sample_toast, Toast.LENGTH_LONG).show()

            // Pass values to the secondary activity.
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(KEY_USERNAME, etUsername.editText?.text.toString())
                putExtra(KEY_PASS, etPassword.editText?.text.toString())
            }
            startActivity(intent)
        }
    }

    /**
     * Save UI state for configuration changes.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, etUsername.editText?.text.toString())
        outState.putString(KEY_PASS, etPassword.editText?.text.toString())
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASS = "pass"
    }
}