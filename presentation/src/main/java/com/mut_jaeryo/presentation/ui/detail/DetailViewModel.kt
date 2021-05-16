package com.mut_jaeryo.presentation.ui.detail

import androidx.lifecycle.*
import com.mut_jaeryo.domain.usecase.SetDrawingHeartUseCase
import com.mut_jaeryo.domain.usecase.SetDrawingReportUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
        private val setDrawingHeartUseCase: SetDrawingHeartUseCase,
        private val setDrawingReportUseCase: SetDrawingReportUseCase,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _drawingItem = savedStateHandle.getLiveData<DrawingItem>("drawingId")
    val drawingItem: LiveData<DrawingItem> = _drawingItem
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: SingleLiveEvent<String> = _errorMessage
    private val _favoriteCount = MutableLiveData(drawingItem.value?.heartCount ?: 0)
    val favoriteCount: LiveData<Int> = _favoriteCount

    fun reportDrawing() = viewModelScope.launch {
        _drawingItem.value?.let {
            setDrawingReportUseCase(it.toDomain())
        }
    }

    fun changeDrawingHeart() = viewModelScope.launch {
        _drawingItem.value?.let {
            setDrawingHeartUseCase(it.toDomain())

            _favoriteCount.postValue ((_favoriteCount.value ?: 0) + 1)
        }
    }
}