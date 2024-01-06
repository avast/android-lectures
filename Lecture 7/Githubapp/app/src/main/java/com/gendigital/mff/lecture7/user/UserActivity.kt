package com.gendigital.mff.lecture7.user

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gendigital.mff.lecture7.databinding.ActivityUserBinding
import com.gendigital.mff.lecture7.search.SearchFragment
import com.gendigital.mff.lecture7.user.UserFragment.Companion.addUserFragment

/**
 * Activity showing user fragment
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
}