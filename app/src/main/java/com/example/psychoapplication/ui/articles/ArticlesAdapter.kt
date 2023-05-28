package com.example.psychoapplication.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psychoapplication.data.model.Article
import com.example.psychoapplication.databinding.ArticlesItemBinding

class ArticlesAdapter(
    private val onItemClicked: (Int, Article) -> Unit
) : ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback) {

    inner class ArticlesViewHolder(private val binding: ArticlesItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.title.text = article.title
            binding.contentDescription.text = article.content
            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ArticlesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}
