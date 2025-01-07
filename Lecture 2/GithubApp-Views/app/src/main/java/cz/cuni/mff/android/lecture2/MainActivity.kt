package cz.cuni.mff.android.lecture2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.cuni.mff.android.lecture2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.btnSubmit.setOnClickListener {
            onBtnSubmitClick(binding.etUsernameLayout.editText?.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, binding.etUsernameLayout.editText?.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val username = savedInstanceState.getString(KEY_USERNAME).orEmpty()
        binding.etUsernameLayout.editText?.setText(username)
    }

    private fun onBtnSubmitClick(username: String) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra(KEY_USERNAME, username)
        }

        startActivity(intent)
    }

    companion object {
        const val KEY_USERNAME = "username"
    }
}