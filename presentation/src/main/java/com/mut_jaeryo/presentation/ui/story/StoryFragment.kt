package com.mut_jaeryo.presentation.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.FragmentStoryBinding
import com.mut_jaeryo.presentation.mapper.toPresentation
import com.mut_jaeryo.presentation.ui.detail.DetailActivity
import com.mut_jaeryo.presentation.ui.story.adapter.StoryAdapter
import com.tistory.blackjinbase.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story) {

    override var logTag: String = "StoryFragment"

    private val storyViewModel: StoryViewModel by viewModels()
    private val storyAdapter: StoryAdapter by lazy {
        StoryAdapter {
            storyViewModel.setDetailEvent(it.toPresentation())
        }.apply {
            addLoadStateListener { loadStates ->
                if (loadStates.refresh == LoadState.Loading) {
                    binding.drawingStoryProgress.apply {
                        visibility = View.VISIBLE
                        spin()
                    }
                }else {
                    binding.drawingStoryProgress.apply {
                        visibility = View.INVISIBLE
                        stopSpinning()
                    }
                }
            }
        }
    }

    companion object {
        const val SPAN_COUNT = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = storyViewModel

        initLayout()

        initRecyclerView()
        observeViewModel()
    }

    private fun initLayout() {
        binding.storySortAll.setOnClickListener {
            storyViewModel.setStoryMode(StoryMode.ALL)
        }
        binding.storySortKeyword.setOnClickListener {
            storyViewModel.setStoryMode(StoryMode.KEYWORD)
        }
    }

    private fun initRecyclerView() {
        binding.storyRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, LinearLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            addItemDecoration(StoryDecoration(40))
            adapter = storyAdapter
        }
    }

    private fun observeViewModel() {
        storyViewModel.storyMode.observe(viewLifecycleOwner) {
            when (it) {
                StoryMode.ALL -> {
                    storyViewModel.getDrawingAll()
                    binding.storySortAll.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_shape_square, null)
                        backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                    binding.storySortKeyword.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_round_ripple_stroke, null)
                        backgroundTintList = null
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }

                else -> {
                    storyViewModel.getDrawingWithKeyword()
                    binding.storySortKeyword.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_shape_square, null)
                        backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                    binding.storySortAll.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_round_ripple_stroke, null)
                        backgroundTintList = null
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }
            }
        }

        storyViewModel.showDetailEventWithItem.observe(viewLifecycleOwner) {
            val intent = Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra("drawing", it)
            }
            requireActivity().startActivity(intent)
        }
        storyViewModel.storyList.observe(viewLifecycleOwner) { pagingData ->
            pagingData?.let {  storyAdapter.submitData(viewLifecycleOwner.lifecycle, it) }
        }
    }
}
