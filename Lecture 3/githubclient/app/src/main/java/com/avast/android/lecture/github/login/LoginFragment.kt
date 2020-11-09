package com.avast.android.lecture.github.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.Settings
import com.avast.android.lecture.github.user.UserActivity
import com.avast.android.lecture.github.user.UserFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment() {

    val settings: Settings by lazy {
        Settings.getInstance(requireContext())
    }

    /**
     * Inflate view hierarchy.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, true)
    }

    /**
     * Setup view actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txt_username.setText(settings.getLastUsername())

        btn_search.setOnClickListener {
            val login = txt_username.text.toString()
            val intent = Intent(activity, UserActivity::class.java)
            intent.putExtra(UserFragment.KEY_USERNAME, login)

            fireNotification(login)

            startActivity(intent)
        }
    }

    /**
     * Fire notification.
     */
    private fun fireNotification(login: String) {
        //TODO: exercise 4/5
    }
}