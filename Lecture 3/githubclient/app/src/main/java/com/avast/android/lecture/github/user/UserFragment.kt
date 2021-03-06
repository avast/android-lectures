package com.avast.android.lecture.github.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user.*

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
        user = dataRepository.getUser(arguments?.getString(KEY_USERNAME).orEmpty())

        user?.run {
            txt_username_value.text = login
            txt_url_value.text = url
            txt_followers_value.text = followers.toString()
            txt_repositories_value.text = public_repos.toString()
            Glide.with(this@UserFragment).load(avatar_url).into(img_avatar)
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