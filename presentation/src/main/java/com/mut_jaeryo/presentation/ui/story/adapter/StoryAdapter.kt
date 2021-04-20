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
        private val clickEvent: (DrawingItem) -> Unit
) : ListAdapter<DrawingItem, StoryHolder>(DrawingItem.DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        return StoryHolder(
                ItemDrawingBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false), clickEvent
        )
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StoryHolder(
        private val binding: ItemDrawingBinding,
        private val clickEvent: (DrawingItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

   fun bind(item: DrawingItem) {
       binding.item = item
       binding.root.setOnClickListener {
           clickEvent(item)
       }
   }
}

