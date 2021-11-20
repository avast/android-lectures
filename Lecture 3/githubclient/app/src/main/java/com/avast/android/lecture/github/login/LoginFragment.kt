package com.avast.android.lecture.github.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.Settings
import com.avast.android.lecture.github.databinding.FragmentLoginBinding
import com.avast.android.lecture.github.user.UserActivity
import com.avast.android.lecture.github.user.UserFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment(R.layout.fragment_login) {

    private var fragmentLoginBinding: FragmentLoginBinding? = null

    private val settings: Settings by lazy {
        Settings.getInstance(requireContext())
    }

    /**
     * Setup view actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentLoginBinding = binding

        lifecycleScope.launch {
            settings.getLastUsername().collect { username ->
                binding.txtUsername.setText(username)
            }
        }

        binding.btnSearch.setOnClickListener {
            val login = binding.txtUsername.text.toString()
            val intent = Intent(activity, UserActivity::class.java)
            intent.putExtra(UserFragment.KEY_USERNAME, login)

            lifecycleScope.launch {
                settings.setLastUsername(login)
            }

            startActivity(intent)
        }
    }
}