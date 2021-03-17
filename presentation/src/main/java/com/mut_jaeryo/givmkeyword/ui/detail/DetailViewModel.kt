package com.mut_jaeryo.givmkeyword.ui.detail

import androidx.lifecycle.*
import com.mashup.domain.common.Result
import com.mashup.domain.usecase.GetFavoriteList
import com.mut_jaeryo.givmkeyword.entities.DrawingItem
import com.mut_jaeryo.givmkeyword.entities.FavoriteItem
import com.mut_jaeryo.givmkeyword.mapper.toDomain
import com.mut_jaeryo.givmkeyword.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
        private val getFavoriteList: GetFavoriteList,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _drawing = savedStateHandle.getLiveData<DrawingItem>("drawing")
    val drawingItem: LiveData<DrawingItem> = _drawing
    private val _favoriteList = MutableLiveData<List<FavoriteItem>>()
    val favoriteItemList: LiveData<List<FavoriteItem>> = _favoriteList

    fun reportDrawing() {

    }

    fun changeDrawingHeart(isDoubleClick: Boolean) {

    }

    fun loadFavoriteList() = viewModelScope.launch {
        _drawing.value?.let {
            getFavoriteList(it.toDomain()).let { result ->
                if (result is Result.Success) {
                    _favoriteList.value = result.data.toPresentation()
                }
            }
        }
    }
}