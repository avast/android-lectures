package com.avast.mff.lecture5.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.avast.mff.lecture5.data.GithubRepository
import com.avast.mff.lecture5.databinding.ItemRepositoryBinding

/**
 * Recycler adapter for showing list of [GithubRepository].
 *
 * @property onItemClickListener action what should happen when user click on single item.
 */
class UserRepositoriesRecyclerAdapter(
    private val onItemClickListener: OnRepositoryClickListener
) : RecyclerView.Adapter<RepositoryViewHolder>() {

    var data: List<GithubRepository> = listOf()
        set(value) {
            field = value
            // Custom setter to let adapter know, data were changed. In production implementation, it
            // should call more suitable notify* method, which will be much more efficient.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding) { position: Int ->
            onItemClickListener.onRepositoryClick(data[position])
        }
     }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

/**
 * Memoize view within single item in list.
 */
class RepositoryViewHolder(
    private val binding: ItemRepositoryBinding,
    onItemClicked: (Int) -> Unit,
) : ViewHolder(binding.root) {

    init {
        // When user clicks on some item, notify the listener with item position within the adapter.
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(githubRepository: GithubRepository) {
        binding.txtRepositoryName.text = githubRepository.name
        binding.txtRepositoryDescription.text = githubRepository.description
    }

}

fun interface OnRepositoryClickListener {
    fun onRepositoryClick(githubRepository: GithubRepository)
}