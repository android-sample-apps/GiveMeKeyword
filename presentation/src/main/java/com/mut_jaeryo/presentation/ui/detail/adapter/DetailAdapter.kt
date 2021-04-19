package com.mut_jaeryo.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.presentation.databinding.ItemFavoriteBinding
import com.mut_jaeryo.presentation.entities.FavoriteItem
import com.mut_jaeryo.presentation.ui.detail.DetailViewModel

class DetailAdapter(private val viewModel: DetailViewModel) :
        ListAdapter<FavoriteItem, DetailHolder>(DetailDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        return DetailHolder(
                ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false), viewModel
        )
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DetailHolder(
        private val binding: ItemFavoriteBinding,
        private val viewModel: DetailViewModel
        ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FavoriteItem) {
        binding.item = item
        binding.executePendingBindings()
    }
}

object DetailDiffUtil: DiffUtil.ItemCallback<FavoriteItem>() {
    override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
        return oldItem == newItem
    }
}