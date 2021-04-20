package com.mut_jaeryo.presentation.ui.story

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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

    private val storyViewModel: StoryViewModel by viewModels()

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
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(StoryDecoration(40))
        }
    }

    private fun observeViewModel() {
        storyViewModel.storyMode.observe(viewLifecycleOwner) {
            when (it) {
                StoryMode.ALL -> {
                    binding.storySortAll.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_shape_square, null)
                        backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                    binding.storySortKeyword.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_round_ripple, null)
                        backgroundTintList = null
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }

                else -> {
                    binding.storySortKeyword.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_shape_square, null)
                        backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorAccent)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                    binding.storySortAll.apply {
                        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_round_ripple, null)
                        backgroundTintList = null
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }
            }
        }
        storyViewModel.isStoryLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.drawingStoryProgress.apply {
                    visibility = View.VISIBLE
                }
            } else {
                binding.drawingStoryProgress.stopSpinning()
            }
        }
        storyViewModel.showDetailEventWithItem.observe(viewLifecycleOwner) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra("id",it)
            }
            requireActivity().startActivity(intent)
        }
    }
}
