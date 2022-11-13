package com.avast.mff.lecture4.user

import android.view.View
import com.avast.mff.lecture4.data.GithubRepository
import com.avast.mff.lecture4.databinding.ItemRepositoryBinding

class UserRepositoriesRecyclerAdapter(
    private val onClick: OnRepositoryClick
) {

    var data: List<GithubRepository> = listOf()
        set(value) {
            field = value
        }
}

class RepositoryViewHolder(
    itemView: View,
    private val binding: ItemRepositoryBinding,
    onItemClicked: (Int) -> Unit,
) {

}

fun interface OnRepositoryClick {
    fun onRepositoryClick(githubRepository: GithubRepository)
}