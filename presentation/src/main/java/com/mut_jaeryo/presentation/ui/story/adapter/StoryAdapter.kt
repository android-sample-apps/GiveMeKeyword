package com.mut_jaeryo.presentation.ui.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.presentation.databinding.ItemDrawingBinding
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.ui.story.StoryViewModel


class StoryAdapter(
        private val viewModel: StoryViewModel
) : ListAdapter<DrawingItem, StoryHolder>(DrawingItem.DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        return StoryHolder(
                ItemDrawingBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false), viewModel
        )
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StoryHolder(
        private val binding: ItemDrawingBinding,
        private val viewModel: StoryViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            viewModel.setDetailEvent(adapterPosition)
        }
    }

   fun bind(item: DrawingItem) {
       binding.item = item
       binding.executePendingBindings()
   }
}

