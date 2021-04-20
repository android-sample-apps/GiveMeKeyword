package com.mut_jaeryo.presentation.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.usecase.GetDrawingAllUseCase
import com.mut_jaeryo.domain.usecase.GetDrawingKeywordUseCase
import com.mut_jaeryo.domain.usecase.GetKeywordUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
        private val getKeywordUseCase: GetKeywordUseCase,
        private val getDrawingKeywordUseCase: GetDrawingKeywordUseCase,
        private val getDrawingAllUseCase: GetDrawingAllUseCase
) : ViewModel() {
    private val _storyMode = MutableLiveData(StoryMode.ALL)
    val storyMode: LiveData<StoryMode> = _storyMode
    private val _storyList = MutableLiveData<List<DrawingItem>>(emptyList())
    val storyList: LiveData<List<DrawingItem>> = _storyList
    private val _showDetailEventWithItem = MutableLiveData<DrawingItem>()
    val showDetailEventWithItem: LiveData<DrawingItem> = _showDetailEventWithItem
    private val _isStoryLoading = SingleLiveEvent<Boolean>()
    val isStoryLoading: SingleLiveEvent<Boolean> = _isStoryLoading

    fun setStoryMode(mode: StoryMode) {
        _storyMode.value = mode

        when (mode) {
            StoryMode.ALL -> getDrawingAll()
            StoryMode.KEYWORD -> getDrawingWithKeyword()
        }
    }

    fun getDrawingAll() = viewModelScope.launch {
        _isStoryLoading.postValue(true)
        getDrawingAllUseCase(Unit).let {
            if (it is Result.Success) {
                _storyList.postValue(it.data.toPresentation())
                _isStoryLoading.postValue(false)
            }
        }
    }

    fun getKeywordAsync() = viewModelScope.async {
        getKeywordUseCase(Unit).let { result ->
            if (result is Result.Success) {
                result.data.keyword
            } else {
                null
            }
        }
    }

    private fun getDrawingWithKeyword() = viewModelScope.launch {
        _isStoryLoading.postValue(true)
        getKeywordAsync().await()?.let { keyword ->
            getDrawingKeywordUseCase(keyword).let {
                if (it is Result.Success) {
                    _storyList.postValue(it.data.toPresentation())
                    _isStoryLoading.postValue(false)
                }
            }
        }
    }

    fun setDetailEvent(item: DrawingItem) {
        _showDetailEventWithItem.value = item
    }
}