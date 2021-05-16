package com.mut_jaeryo.presentation.ui.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.presentation.databinding.ItemCommentBinding

class CommentAdapter(
        private val deleteEvent: (Comment) -> Unit
) : PagingDataAdapter<Comment, CommentHolder>(CommentDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(
                ItemCommentBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false), deleteEvent
        )
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class CommentHolder(
        private val binding: ItemCommentBinding,
        private val deleteEvent: (Comment) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Comment) {
        binding.item = item
        binding.commentDelete.setOnClickListener {
            deleteEvent(item)
        }
    }
}

object CommentDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}

