package com.mut_jaeryo.presentation.ui.drawing

import android.graphics.Color
import androidx.lifecycle.*
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.usecase.*
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
        private val getKeywordUseCase: GetKeywordUseCase,
        private val requestNewKeywordUseCase: RequestNewKeywordUseCase,
        private val getKeywordRequestCountUseCase: GetKeywordRequestCountUseCase
): ViewModel() {
    private val _selectedDrawingMode = MutableLiveData(DrawingMode.BRUSH)
    val selectedDrawingMode: LiveData<DrawingMode> = _selectedDrawingMode
    private val _unSelectedDrawingMode = MutableLiveData<DrawingMode>()
    val unSelectedDrawingMode = _unSelectedDrawingMode
    private val _selectedDrawingColor = MutableLiveData(Color.BLACK)
    val selectedDrawingColor = _selectedDrawingColor
    private val _adMobDialogEvent = SingleLiveEvent<Unit>()
    val adMobDialogEvent: SingleLiveEvent<Unit> = _adMobDialogEvent

    private val _keyword = MutableLiveData<String>()
    val keyword: LiveData<String> = _keyword

    fun loadKeyword() = viewModelScope.launch {
        getKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                _keyword.value = it.data.keyword
            }
        }
    }

    fun requestNewKeyword() = viewModelScope.launch {
        getKeywordRequestCountUseCase(Unit).let {
            if (it is Result.Success) {
                if (it.data > FREE_MAX_REQUEST_COUNT) {
                    _adMobDialogEvent.value = Unit
                }
            }
        }
    }

    fun getNewKeyword() = viewModelScope.launch {
        requestNewKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                _keyword.value = it.data.keyword
            }
        }
    }

    fun setBrushColor(color: Int) {
        _selectedDrawingColor.value = color
    }

    companion object {
        private const val FREE_MAX_REQUEST_COUNT = 3
    }
}