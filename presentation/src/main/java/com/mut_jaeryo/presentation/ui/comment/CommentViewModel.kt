package com.mut_jaeryo.presentation.ui.comment

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.domain.usecase.CreateCommentUseCase
import com.mut_jaeryo.domain.usecase.GetCommentUseCase
import com.mut_jaeryo.domain.usecase.GetUserUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
        private val getCommentUseCase: GetCommentUseCase,
        private val createCommentUseCase: CreateCommentUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _commentList = MutableLiveData<PagingData<Comment>>()
    val commentList: LiveData<PagingData<Comment>> = _commentList

    val editComment = MutableLiveData("")

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean>
    get() = _isLoading

    init {
        savedStateHandle.get<String>("drawingId")?.let { drawingId ->
            getComments(drawingId)
        }
    }

    private fun getComments(drawingId: String) = viewModelScope.launch {
        getCommentUseCase(drawingId).let { result ->
            if (result is Result.Success) {
                result.data.cachedIn(viewModelScope).collect {
                    _commentList.value = it
                }
            }
        }
    }

    private fun getUserId() = viewModelScope.async {
        getUserUseCase(Unit).let {
            if (it is Result.Success) {
                return@async it.data?.name
            } else if (it is Result.Error){
                Log.e("CommentViewModel", it.exception.stackTraceToString())
            }
        }
        null
    }

    fun uploadComment() = viewModelScope.launch {
        val inputComment = editComment.value
        if (inputComment.isNullOrEmpty()) {
            return@launch
        }

        editComment.value = ""
        val comment = Comment(
            drawingId = savedStateHandle.get<String>("drawingId") ?: return@launch,
                userId = getUserId().await() ?: return@launch,
                comment = inputComment
        )
        createCommentUseCase.invoke(comment).let {
            if (it is Result.Success) {
                savedStateHandle.get<String>("drawingId")?.let { drawingId ->
                    getComments(drawingId)
                }
        } else if (it is Result.Error) {
                Log.e("CommentViewModel", it.exception.stackTraceToString())
            }
        }
    }
}