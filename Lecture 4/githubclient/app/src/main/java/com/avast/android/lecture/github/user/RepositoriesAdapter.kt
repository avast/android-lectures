package com.avast.android.lecture.github.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.databinding.ItemRepositoryBinding

class RepositoriesAdapter(repositories: List<GithubRepository>) : RecyclerView.Adapter<RepositoryViewHolder>() {

    //TODO: task

    var repositories = repositories
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 0

}

class RepositoryViewHolder(binding: ItemRepositoryBinding): RecyclerView.ViewHolder(binding.root) {

    private val txtRepositoryName: TextView = binding.txtRepositoryName
    private val txtRepositoryDescription: TextView = binding.txtRepositoryDescription

    fun bind(repository: GithubRepository) {
        txtRepositoryName.text = repository.name
        txtRepositoryDescription.text = repository.description
    }
}