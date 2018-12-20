package com.avast.android.lecture.github.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.avast.android.lecture.github.R

/**
 * Login activity which holds [LoginFragment]. Fragment is inflated by XML.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
