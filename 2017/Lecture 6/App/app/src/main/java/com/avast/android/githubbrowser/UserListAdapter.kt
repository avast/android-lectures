package com.avast.android.githubbrowser

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.support.v7.recyclerview.extensions.DiffCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avast.android.githubbrowser.pojo.User
import com.avast.android.githubbrowser.ui.UserProfileActivity
import com.avast.android.githubbrowser.ui.UserViewHolder

/**
 * Created by lukas on 12/22/17.
 */
class UserListAdapter : PagedListAdapter<User, UserViewHolder>(diffCall) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view, object : OnItemClickListener {
            override fun onItemClicked(view: View, position: Int) {
                onClick(view, position)
            }
        })
    }

    override fun onBindViewHolder(holder: UserViewHolder?, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder?.bindTo(user)
        } else {
            holder?.clear()
        }
    }

    fun onClick(view: View, position: Int) {
        val user = getItem(position)
        val i = Intent(view.context, UserProfileActivity::class.java)
        i.putExtra(UserProfileActivity.EXTRA_USER_ID, user?.id ?: -1)
        i.putExtra(UserProfileActivity.EXTRA_USERNAME, user?.login ?: "")
        view.context.startActivity(i)
    }

    companion object {
        val diffCall = object: DiffCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }

        interface OnItemClickListener {
            fun onItemClicked(view: View, position: Int)
        }
    }
}