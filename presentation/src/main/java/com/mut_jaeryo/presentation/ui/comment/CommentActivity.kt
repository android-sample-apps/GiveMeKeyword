package com.mut_jaeryo.presentation.ui.comment

import android.os.Bundle
import android.view.MenuItem
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityCommentBinding
import com.tistory.blackjinbase.base.BaseActivity

class CommentActivity : BaseActivity<ActivityCommentBinding>(R.layout.activity_comment) {
    override var logTag: String = "CommentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarButton()
    }

    private fun initAppBarButton() {
        setSupportActionBar(
                binding.commentToolbar.apply {
                    title = getString(R.string.comment_title)
                }
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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