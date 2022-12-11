package com.avast.mff.lecture6.user

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.avast.mff.lecture6.databinding.ActivityUserBinding
import com.avast.mff.lecture6.search.SearchFragment
import com.avast.mff.lecture6.user.UserFragment.Companion.addUserFragment

/**
 *
 */
class UserActivity : AppCompatActivity() {

    companion object {
        private val TAG = UserActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: $this")
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.addUserFragment(
                binding.fragmentContainerView.id,
                intent.getStringExtra(SearchFragment.KEY_USERNAME).orEmpty()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: $this")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: $this")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: $this")
    }
}