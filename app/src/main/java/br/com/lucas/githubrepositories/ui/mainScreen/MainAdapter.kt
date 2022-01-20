package br.com.lucas.githubrepositories.ui.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lucas.githubrepositories.data.model.Repository
import br.com.lucas.githubrepositories.databinding.RepositoryItemBinding

class MainAdapter : ListAdapter<Repository, MainAdapter.MainViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RepositoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class MainViewHolder(private val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repositoryItem: Repository) {
            binding.tvRepositoryName.text = repositoryItem.name
            binding.tvRepositoryDescription.text = repositoryItem.description
            binding.tvRepositoryLanguage.text = repositoryItem.language
            binding.chipStar.text = repositoryItem.stargazersCount.toString()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem == newItem
    }
}