package com.mut_jaeryo.presentation.ui.story

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.usecase.GetDrawingAllUseCase
import com.mut_jaeryo.domain.usecase.GetDrawingKeywordUseCase
import com.mut_jaeryo.domain.usecase.GetKeywordUseCase
import com.mut_jaeryo.presentation.entities.DrawingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
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

    private val _storyList = MutableLiveData<PagingData<Drawing>>()
    val storyList: LiveData<PagingData<Drawing>> = _storyList

    val keyword = viewModelScope.async {
        getKeywordAsync().await()
    }
    private val _showDetailEventWithItem = MutableLiveData<DrawingItem>()
    val showDetailEventWithItem: LiveData<DrawingItem> = _showDetailEventWithItem

    fun setStoryMode(mode: StoryMode) {
        _storyMode.value = mode
    }

    private fun getKeywordAsync() = viewModelScope.async {
        getKeywordUseCase(Unit).let { result ->
            if (result is Result.Success) {
                result.data.keyword
            } else {
                null
            }
        }
    }

    fun getDrawingAll() = viewModelScope.launch {
        getDrawingAllUseCase(Unit).let {
            if (it is Result.Success) {
                it.data.cachedIn(viewModelScope)
                        .collect { pagingData ->
                            _storyList.postValue(pagingData)
                        }
            }
        }
    }

     fun getDrawingWithKeyword() = viewModelScope.launch {
        keyword.await()?.let { keyword ->
            getDrawingKeywordUseCase(keyword).let {
                if (it is Result.Success) {
                    it.data.cachedIn(viewModelScope)
                            .collect {  pagingData ->
                                _storyList.postValue(pagingData)
                            }
                }
            }
        }
    }

    fun setDetailEvent(item: DrawingItem) {
        _showDetailEventWithItem.value = item
    }
}