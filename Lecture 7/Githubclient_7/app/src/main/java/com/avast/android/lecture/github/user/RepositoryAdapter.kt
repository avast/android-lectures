package com.avast.android.lecture.github.user

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.avast.android.lecture.github.R
import com.avast.android.lecture.github.data.GithubRepository

class RepositoryAdapter(): RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: List<GithubRepository> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false) as View
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(viewHolder: RepositoryViewHolder, position: Int) {
        with (repositories[position]) {
            viewHolder.txtRepositoryName.text = full_name
            viewHolder.txtRepositoryUrl.text = description
        }
    }

    class RepositoryViewHolder(repositoryView: View): RecyclerView.ViewHolder(repositoryView), View.OnClickListener {
        val txtRepositoryName: TextView = repositoryView.findViewById(R.id.txt_repository_name)
        val txtRepositoryUrl: TextView = repositoryView.findViewById(R.id.txt_repository_description)

        init {
            repositoryView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(v.context, "Tapped on ${txtRepositoryName.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}