package com.avast.android.lecture.github.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user.img_avatar
import kotlinx.android.synthetic.main.fragment_user.txt_followers_value
import kotlinx.android.synthetic.main.fragment_user.txt_repositories_value
import kotlinx.android.synthetic.main.fragment_user.txt_url_value
import kotlinx.android.synthetic.main.fragment_user.txt_username_value

/**
 * Fragment to show user detail.
 */
class UserFragment: Fragment() {

    var user: User? = null

    /**
     * Lazily initialize data repository.
     */
    private val dataRepository: Repository by lazy {
        InMemoryRepository()
    }

    /**
     * Inflate view hierarchy to this fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        val username = arguments?.getString(KEY_USERNAME, "")
        if (username != null) {
            user = dataRepository.getUser(username)
            user?.let {
                txt_username_value.text = it.login
                txt_url_value.text = it.url
                txt_followers_value.text = it.followers.toString()
                txt_repositories_value.text = it.public_repos.toString()
                Glide.with(this)
                        .load(it.avatar_url)
                        .into(img_avatar)
            }
        }
    }

    /**
     * Stop any [Glide] operations related to this fragment.
     */
    override fun onStop() {
        super.onStop()
        Glide.with(this).onStop()
    }

    companion object {

        val KEY_USERNAME = "username"

        /**
         * Factory method to create fragment instance. Framework requires empty default constructor.
         */
        @JvmStatic
        fun newInstance(username: String): UserFragment {
            val fragment = UserFragment()
            fragment.arguments = Bundle().apply {
                putString(KEY_USERNAME, username)
            }

            return fragment
        }
    }
}