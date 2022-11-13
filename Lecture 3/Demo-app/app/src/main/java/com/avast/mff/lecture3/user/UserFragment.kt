package com.avast.mff.lecture3.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.avast.mff.lecture3.R
import com.avast.mff.lecture3.databinding.FragmentUserBinding
import com.avast.mff.lecture3.repository.Repository
import com.avast.mff.lecture3.repository.memory.InMemoryRepository
import com.bumptech.glide.Glide

class UserFragment: Fragment(R.layout.fragment_user) {

    private val dataRepository: Repository by lazy {
        InMemoryRepository()
    }

    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        dataRepository.getUser(requireArguments().getString(KEY_USERNAME).orEmpty()).onSuccess {
            binding.cardUser.apply {
                txtUsernameTitle.text = it.login
                txtStars.text = it.followers.toString()
                txtUrl.text = it.html_url
                Glide.with(this@UserFragment).load(it.avatar_url).into(binding.cardUser.imgAvatar)
            }
        }.onFailure {
            Toast.makeText(requireContext(), getString(R.string.txt_user_not_found), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_USERNAME = "username"

        fun FragmentManager.addUserFragment(@IdRes containerViewId: Int, username: String) {
            val args = bundleOf(KEY_USERNAME to username)
            commit {
                setReorderingAllowed(true)
                add<UserFragment>(R.id.fragment_container_view, args = args)
            }
        }
    }
}