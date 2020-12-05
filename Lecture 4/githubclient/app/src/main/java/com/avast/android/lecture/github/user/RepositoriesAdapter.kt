package com.avast.android.lecture.github.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.data.GithubRepository

class RepositoriesAdapter(repositories: List<GithubRepository>) : RecyclerView.Adapter<RepositoryViewHolder>() {

    var repositories = repositories
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)

        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.count()

}

class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var txtRepositoryName: TextView = view.findViewById(R.id.txt_repository_name)
    var txtRepositoryDescription: TextView = view.findViewById(R.id.txt_repository_description)

    fun bind(repository: GithubRepository) {
        txtRepositoryName.text = repository.name
        txtRepositoryDescription.text = repository.description
    }
}