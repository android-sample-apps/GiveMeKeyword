package com.mut_jaeryo.presentation.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.FragmentStoryBinding
import com.mut_jaeryo.presentation.ui.detail.DetailActivity
import com.tistory.blackjinbase.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story) {

    override var logTag: String = "StoryFragment"

    private val viewModel: StoryViewModel by viewModels()

    companion object {
        const val SPAN_COUNT = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLayout()
        initRecyclerView()
        observeViewModel()
    }

    private fun initLayout() {
        binding.storySortAll.setOnClickListener {
            viewModel.setStoryMode(StoryMode.ALL)
        }
        binding.storySortKeyword.setOnClickListener {
            viewModel.setStoryMode(StoryMode.KEYWORD)
        }
    }

    private fun initRecyclerView() {
        binding.storyRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(StoryDecoration(40))
        }
    }

    private fun observeViewModel() {
        viewModel.storyMode.observe(viewLifecycleOwner) {
            when (it) {
                StoryMode.ALL -> {
                    binding.storySortKeyword.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.surface_color)
                    binding.storySortAll.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.githubBlack)
                }

                else -> {
                    binding.storySortAll.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.surface_color)
                    binding.storySortKeyword.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.githubBlack)
                }
            }
        }
        viewModel.isStoryLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.drawingStoryProgress.apply {
                    visibility = View.VISIBLE
                }
            } else {
                binding.drawingStoryProgress.stopSpinning()
            }
        }
        viewModel.showDetailEventWithItem.observe(viewLifecycleOwner) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra("id",it)
            }
            requireActivity().startActivity(intent)
        }
    }

}
