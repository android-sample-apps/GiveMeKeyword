package com.mut_jaeryo.presentation.ui.comment

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityCommentBinding
import com.mut_jaeryo.presentation.ui.comment.adapter.CommentAdapter
import com.tistory.blackjinbase.base.BaseActivity
import com.tistory.blackjinbase.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentActivity : BaseActivity<ActivityCommentBinding>(R.layout.activity_comment) {
    override var logTag: String = "CommentActivity"
    private val commentViewModel: CommentViewModel by viewModels()
    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter {
            commentViewModel.deleteComment(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = commentViewModel

        initAppBarButton()
        initCommentRecyclerView()
        observeViewModel()
    }

    private fun initAppBarButton() {
        setSupportActionBar(
                binding.commentToolbar.apply {
                    title = getString(R.string.comment_title)
                }
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCommentRecyclerView() {
        binding.commentRecyclerview.apply {
            adapter = commentAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            commentViewModel.commentList?.collectLatest {
                commentAdapter.submitData(it)
            }
        }
        commentViewModel.needCreateUser.observe(this) {
            toast(R.string.drawing_create_user_message)
        }
        commentViewModel.isCommentEmpty.observe(this) {
            toast(R.string.comment_empty)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}