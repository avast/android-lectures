package com.avast.android.githubclient.lecture5.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.avast.android.githubclient.lecture5.R
import com.avast.android.githubclient.lecture5.data.GithubRepository

class RepositoryAdapter: RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<GithubRepository> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int = repositories.count()

    override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int) {
        with (repositories[position]) {
            viewHolder.bind(this)
        }
    }

    class RepositoryViewHolder(repositoryView: View): RecyclerView.ViewHolder(repositoryView), View.OnClickListener {
        val txtRepositoryName: TextView = repositoryView.findViewById(R.id.txt_repository_name)
        val txtRepositoryUrl: TextView = repositoryView.findViewById(R.id.txt_repository_description)

        init {
            repositoryView.setOnClickListener(this)
        }

        fun bind(githubRepository: GithubRepository) {
            with(githubRepository) {
                txtRepositoryName.text = name
                txtRepositoryUrl.text = description
            }
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(v.context, "Tapped on ${txtRepositoryName.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}