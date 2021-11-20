package com.avast.android.lecture.github.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avast.android.lecture.github.data.User
import com.avast.android.lecture.github.databinding.FragmentUserBinding
import com.avast.android.lecture.github.repository.Repository
import com.avast.android.lecture.github.repository.memory.InMemoryRepository
import com.bumptech.glide.Glide

/**
 * Fragment to show user detail.
 */
class UserFragment: Fragment() {

    var binding: FragmentUserBinding? = null

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
        val fragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
        binding = fragmentUserBinding
        return fragmentUserBinding.root
    }

    override fun onStart() {
        super.onStart()
        user = dataRepository.getUser(arguments?.getString(KEY_USERNAME).orEmpty())

        withNonNull(user) {
            binding?.let {
                it.txtUsernameValue.text = login
                it.txtUrlValue.text = url
                it.txtFollowersValue.text = "$followers"
                it.txtRepositoriesValue.text = "$public_repos"
                Glide.with(it.imgAvatar).load(avatar_url).into(it.imgAvatar)
            }
        }
    }

    /**
     * The same as [with], but accepts also nullable [receiver] and the block is executed only in case
     * of non-null [receiver].
     */
    private inline fun <T, R> withNonNull(receiver: T?, block: T.() -> R): R? {
        return receiver?.block()
    }

    /**
     * Stop any [Glide] operations related to this fragment.
     */
    override fun onStop() {
        super.onStop()
        Glide.with(this).onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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