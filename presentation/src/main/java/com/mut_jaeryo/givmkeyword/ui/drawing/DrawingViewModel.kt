package com.mut_jaeryo.givmkeyword.ui.drawing

import android.graphics.Color
import androidx.lifecycle.*
import com.mashup.domain.common.Result
import com.mashup.domain.usecase.GetKeywordUseCase
import com.mashup.domain.usecase.RequestNewKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
        private val getKeywordUseCase: GetKeywordUseCase,
        private val requestNewKeywordUseCase: RequestNewKeywordUseCase,
        private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _selectedDrawingMode = MutableLiveData(DrawingMode.BRUSH)
    val selectedDrawingMode: LiveData<DrawingMode> = _selectedDrawingMode
    private val _unSelectedDrawingMode = MutableLiveData<DrawingMode>()
    val unSelectedDrawingMode = _unSelectedDrawingMode
    private val _selectedDrawingColor = MutableLiveData(Color.BLACK)
    val selectedDrawingColor = _selectedDrawingColor

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
        requestNewKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                _keyword.value = it.data.keyword
            }
        }
    }

    fun setBrushColor(color: Int) {
        _selectedDrawingColor.value = color
    }
}