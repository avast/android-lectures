package com.avast.helloworld

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Views are referenced right after setting layout, later in code are always not-null and initialized
    lateinit var btnOpenSecondActivity: Button
    lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup layout
        setContentView(R.layout.activity_main)

        // Getting view references
        btnOpenSecondActivity = findViewById(R.id.btn_submit)
        input = findViewById(R.id.et_text)

        // Restore view state
        savedInstanceState?.let {
            input.isEnabled = it.getBoolean(ENABLED_STATE, true)
        }

        // Open SecondActivity and pass data
        btnOpenSecondActivity.setOnClickListener {
            val text = input.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.KEY_TEXT, text)
            startActivity(intent)
        }

        // Setting listener without remembering button reference
        findViewById<Button>(R.id.btn_show_toast).setOnClickListener {
            val text = input.text.toString()
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        }
    }

    // Store view state to survive configuration changes.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(ENABLED_STATE, input.isEnabled)
    }

    companion object {
        val ENABLED_STATE = "btn_state"
    }
}
