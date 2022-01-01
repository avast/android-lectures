package com.avast.android.lecture.github.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avast.android.lecture.github.data.GithubRepository
import com.avast.android.lecture.github.databinding.ItemRepositoryBinding

class RepositoriesAdapter(
    repositories: List<GithubRepository>,
    val onItemClick: (GithubRepository) -> Unit)
    : RecyclerView.Adapter<RepositoryViewHolder>() {

    var repositories = repositories
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepositoryViewHolder(view) {
            onItemClick(repositories[it])
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size
}

class RepositoryViewHolder(
    binding: ItemRepositoryBinding,
    onItemClick: (Int) -> Unit): RecyclerView.ViewHolder(binding.root) {

    private val txtRepositoryName: TextView = binding.txtRepositoryName
    private val txtRepositoryDescription: TextView = binding.txtRepositoryDescription

    init {
        itemView.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(repository: GithubRepository) {
        txtRepositoryName.text = repository.name
        txtRepositoryDescription.text = repository.description
    }
}
