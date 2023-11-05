package com.example.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld.databinding.ActivitySecondaryBinding

class SecondaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondaryBinding

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            binding.txtUsername.text = it.getStringExtra(KEY_USERNAME)
            binding.txtPassword.text = it.getStringExtra(KEY_PASSWORD)
        }
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }
}