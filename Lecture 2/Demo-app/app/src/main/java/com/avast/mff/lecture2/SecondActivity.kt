package com.avast.mff.lecture2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.avast.mff.lecture2.databinding.ActivitySecondBinding

/**
 * In this class you can see example how to use
 * * Android view binding
 * * How to read values passed from the previous activity
 * * How to use string resources with placeholders
 */
class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            binding.txtSecondActivity.text = getString(R.string.txt_secondary_activity_text, it.getStringExtra(MainActivity.KEY_USERNAME))
        }
    }
}