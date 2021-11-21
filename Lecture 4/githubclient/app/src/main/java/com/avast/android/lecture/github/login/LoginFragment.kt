package com.avast.android.lecture.github.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.avast.android.lecture.github.Settings
import com.avast.android.lecture.github.databinding.FragmentLoginBinding
import com.avast.android.lecture.github.user.UserActivity
import com.avast.android.lecture.github.user.UserFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val settings: Settings by lazy {
        Settings(requireContext())
    }

    /**
     * Inflate view hierarchy.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Setup view actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO: task
        lifecycleScope.launch {
            binding.txtUsername.editText?.setText(settings.getLastUsername().first())
        }

        binding.btnSearch.setOnClickListener {
            val login = binding.txtUsername.editText?.text.toString()

            lifecycleScope.launch {
                settings.setLastUsername(login)
            }
            val intent = Intent(activity, UserActivity::class.java)
            intent.putExtra(UserFragment.KEY_USERNAME, login)

            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}