package com.mut_jaeryo.presentation.ui.detail

import androidx.lifecycle.*
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.usecase.GetFavoriteListUseCase
import com.mut_jaeryo.domain.usecase.SetDrawingHeartUseCase
import com.mut_jaeryo.domain.usecase.SetDrawingReportUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.entities.FavoriteItem
import com.mut_jaeryo.presentation.mapper.toDomain
import com.mut_jaeryo.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
        private val getFavoriteListUseCase: GetFavoriteListUseCase,
        private val setDrawingHeartUseCase: SetDrawingHeartUseCase,
        private val setDrawingReportUseCase: SetDrawingReportUseCase,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _drawingItem = savedStateHandle.getLiveData<DrawingItem>("drawing")
    val drawingItem: LiveData<DrawingItem> = _drawingItem
    private val _favoriteList = MutableLiveData<List<FavoriteItem>>(emptyList())
    val favoriteItemList: LiveData<List<FavoriteItem>> = _favoriteList
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: SingleLiveEvent<String> = _errorMessage

    fun reportDrawing() = viewModelScope.launch {
        _drawingItem.value?.let {
            setDrawingReportUseCase(it.toDomain())
        }
    }

    fun changeDrawingHeart() = viewModelScope.launch {
        _drawingItem.value?.let {
            setDrawingHeartUseCase(it.toDomain())
        }
    }

    fun loadFavoriteList() = viewModelScope.launch {
        _drawingItem.value?.let {
            getFavoriteListUseCase(it.toDomain()).let { result ->
                if (result is Result.Success) {
                    _favoriteList.value = result.data.toPresentation()
                }
            }
        }
    }
}