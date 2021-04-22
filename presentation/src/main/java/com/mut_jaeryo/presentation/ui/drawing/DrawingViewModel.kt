package com.mut_jaeryo.presentation.ui.drawing

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
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
        private val getKeywordRequestCountUseCase: GetKeywordRequestCountUseCase,
        private val getDrawingCachePathUseCase: GetDrawingCachePathUseCase,
        private val getUserUseCase: GetUserUseCase
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
    private val _drawingCacheImageUrl = SingleLiveEvent<String?>()
    val drawingCacheImageUrl: SingleLiveEvent<String?> = _drawingCacheImageUrl
    private val _needCreateUser = SingleLiveEvent<Unit>()
    val needCreateUser: SingleLiveEvent<Unit> = _needCreateUser

    fun loadKeyword() = viewModelScope.launch {
        getKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                _keyword.postValue(it.data.keyword)
            }
        }
    }

    fun requestNewKeyword() = viewModelScope.launch {
        getKeywordRequestCountUseCase(Unit).let {
            if (it is Result.Success) {
                if (it.data >= FREE_MAX_REQUEST_COUNT) {
                    _adMobDialogEvent.postValue(Unit)
                } else {
                    getNewKeyword()
                }
            }
        }
    }

    fun getNewKeyword() = viewModelScope.launch {
        requestNewKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                _keyword.postValue(it.data.keyword)
            }
        }
    }

    fun setDrawingMode(drawingMode: DrawingMode) {
        _unSelectedDrawingMode.value = _selectedDrawingMode.value
        _selectedDrawingMode.value = drawingMode
    }

    fun setBrushColor(color: Int) {
        _selectedDrawingColor.value = color
    }

    fun requestDrawingUpload(bitmap: Bitmap) = viewModelScope.launch {
        getUserUseCase(Unit).let { result ->
            if (result is Result.Success) {
                if (result.data == null) {
                    _needCreateUser.postValue(Unit)
                } else {
                    getDrawingCachePath(bitmap)
                }
            }
        }
    }

    private fun getDrawingCachePath(bitmap: Bitmap) = viewModelScope.launch {
        getDrawingCachePathUseCase(bitmap).let {
            if (it is Result.Success) {
                _drawingCacheImageUrl.postValue(it.data)
            }
        }
    }

    companion object {
        private const val FREE_MAX_REQUEST_COUNT = 3
    }
}