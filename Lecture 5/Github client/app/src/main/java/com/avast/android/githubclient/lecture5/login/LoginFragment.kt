package com.avast.android.githubclient.lecture5.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.avast.android.githubclient.lecture5.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Login to our application. Offers user to enter username.
 */
class LoginFragment: Fragment() {

    /**
     * Inflate view hierarchy.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    /**
     * Setup view actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_search.setOnClickListener {
            val login = txt_username.text.toString()
            val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(login)
            findNavController().navigate(action)
        }
    }
}