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
import com.mut_jaeryo.domain.usecase.DeleteCommentUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
        private val getCommentUseCase: GetCommentUseCase,
        private val createCommentUseCase: CreateCommentUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val deleteCommentUseCase: DeleteCommentUseCase,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _commentList = MutableLiveData<PagingData<Comment>>()
    val commentList: LiveData<PagingData<Comment>> = _commentList

    val editComment = MutableLiveData("")

    private val _needCreateUser = SingleLiveEvent<Unit>()
    val needCreateUser: SingleLiveEvent<Unit> = _needCreateUser

    private val _isCommentEmpty = SingleLiveEvent<Unit>()
    val isCommentEmpty: SingleLiveEvent<Unit> = _isCommentEmpty

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoading: SingleLiveEvent<Boolean>
        get() = _isLoading

    private val drawingId: String?
        get() = savedStateHandle.get<String>("drawingId")

    private val userId: String?
        get() = savedStateHandle.get<String>("userId")

    init {
        getUserId()
    }

    private fun getComments(drawingId: String) = viewModelScope.launch {
        getCommentUseCase(
                drawingId to (userId ?: "알 수 없음")
        ).let { result ->
            if (result is Result.Success) {
                result.data.cachedIn(viewModelScope).collect {
                    _commentList.value = it
                }
            }
        }
    }

    private fun getUserId() = viewModelScope.launch {
        getUserUseCase(Unit).let {
            if (it is Result.Success) {
                savedStateHandle["userId"] = it.data?.name
            } else if (it is Result.Error) {
                Log.e("CommentViewModel", it.exception.stackTraceToString())
            }
        }
    }

    fun uploadComment() = viewModelScope.launch {
        if (userId == null) {
            _needCreateUser.call()
            return@launch
        }
        _isLoading.postValue(true)
        val inputComment = editComment.value
        if (inputComment.isNullOrEmpty()) {
            _isCommentEmpty.call()
            return@launch
        }

        editComment.value = ""
        val comment = Comment(
                drawingId = drawingId ?: return@launch,
                userId = userId ?: return@launch,
                comment = inputComment
        )
        createCommentUseCase.invoke(comment).let {
            _isLoading.postValue(false)
            if (it is Result.Success) {
                drawingId?.let { drawingId ->
                    getComments(drawingId)
                }
            } else if (it is Result.Error) {
                Log.e("CommentViewModel", it.exception.stackTraceToString())
            }
        }
    }

    fun deleteComment(comment: Comment) = viewModelScope.launch {
        _isLoading.postValue(true)
        deleteCommentUseCase.invoke(comment).let {
            _isLoading.postValue(false)
            if (it is Result.Success) {
                drawingId?.let { drawingId ->
                    getComments(drawingId)
                }
            } else if (it is Result.Error) {
                Log.e("CommentViewModel", it.exception.stackTraceToString())
            }
        }
    }
}