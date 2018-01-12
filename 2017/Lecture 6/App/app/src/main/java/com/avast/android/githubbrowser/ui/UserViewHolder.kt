package com.avast.android.githubbrowser.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.avast.android.githubbrowser.UserListAdapter.Companion
import com.avast.android.githubbrowser.pojo.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.txtId
import kotlinx.android.synthetic.main.item_user.txtUsername

/**
 * Created by lukas on 12/22/17.
 */
class UserViewHolder: RecyclerView.ViewHolder, LayoutContainer, View.OnClickListener {

    override val containerView: View
    private val clickListener: Companion.OnItemClickListener

    constructor(containerView: View, clickListener: Companion.OnItemClickListener) : super(containerView) {
        this.containerView = containerView
        this.containerView.setOnClickListener(this)
        this.clickListener = clickListener
    }


    fun bindTo(user: User) {
        txtId.text = user.id.toString()
        txtUsername.text = user.login
    }

    fun clear() {
        txtId.text = ""
        txtUsername.text = ""
    }

    override fun onClick(v: View) {
        clickListener.onItemClicked(v, adapterPosition)
    }
}