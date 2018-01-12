package com.avast.android.githubbrowser.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avast.android.githubbrowser.R
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.repository.UserRepository
import com.avast.android.githubbrowser.viewmodel.UserProfileViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_profile.txtIdContent
import kotlinx.android.synthetic.main.activity_user_profile.txtLoginContent
import kotlinx.android.synthetic.main.activity_user_profile.txtUsername
import javax.inject.Inject

/**
 * Created by lukas on 12/21/17.
 */
class UserProfileActivity: AppCompatActivity() {

    private lateinit var viewModel: UserProfileViewModel

    @Inject
    lateinit var userRepository: UserRepository

    companion object {
        val EXTRA_USER_ID = "user_id"
        val EXTRA_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val userId = intent.extras.getString(EXTRA_USERNAME, "avast")
        viewModel = getViewModel()
        viewModel.init(userId)

        viewModel.user.observe(this, Observer<User> { user ->
            txtIdContent.text = user?.id.toString()
            txtUsername.text = user?.name
            txtLoginContent.text = user?.login
        })
    }

    private fun getViewModel(): UserProfileViewModel {
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val model = UserProfileViewModel(userRepository)
                return model as T
            }

        })[UserProfileViewModel::class.java]
    }

}