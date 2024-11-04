package cz.cuni.mff.android.lecture2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.cuni.mff.android.lecture2.databinding.ActivityDetailBinding

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with (intent) {
            val username = getStringExtra(MainActivity.KEY_USERNAME).orEmpty()
            binding.txtUsername.text = username
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}