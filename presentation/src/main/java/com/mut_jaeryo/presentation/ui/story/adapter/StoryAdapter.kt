package com.mut_jaeryo.presentation.ui.story.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.presentation.databinding.ItemDrawingBinding
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.mapper.toPresentation


class StoryAdapter(
        private val clickEvent: (Drawing) -> Unit
) : PagingDataAdapter<Drawing, StoryHolder>(DrawingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        return StoryHolder(
                ItemDrawingBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false), clickEvent
        )
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class StoryHolder(
        private val binding: ItemDrawingBinding,
        private val clickEvent: (Drawing) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

   fun bind(item: Drawing) {
       binding.item = item.toPresentation()
       binding.root.setOnClickListener {
           clickEvent(item)
       }
   }
}

object DrawingDiffCallback : DiffUtil.ItemCallback<Drawing>() {
        override fun areItemsTheSame(oldItem: Drawing, newItem: Drawing): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Drawing, newItem: Drawing): Boolean {
            return oldItem == newItem
        }
    }

