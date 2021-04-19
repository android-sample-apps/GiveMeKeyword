package com.mut_jaeryo.presentation.ui.upload

import androidx.lifecycle.*
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.usecase.GetKeywordUseCase
import com.mut_jaeryo.domain.usecase.GetUserUseCase
import com.mut_jaeryo.domain.usecase.UploadImageUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
        private val uploadImageUseCase: UploadImageUseCase,
        private val getKeywordUseCase: GetKeywordUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _drawingImageUrl = savedStateHandle.getLiveData<String>("imageUrl")
    val drawingImageUrl: LiveData<String> = _drawingImageUrl
    val drawingContent = MutableLiveData<String>()
    private val _isUploading = SingleLiveEvent<Boolean>()
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    val isUploading: SingleLiveEvent<Boolean> = _isUploading
    private val _isUploadSuccess = SingleLiveEvent<Boolean>()
    val isUploadSuccess: SingleLiveEvent<Boolean> = _isUploadSuccess
    private val _uploadErrorMessage = SingleLiveEvent<String>()
    val uploadErrorMessage: SingleLiveEvent<String> = _uploadErrorMessage

    private suspend fun getKeywordAsync() = viewModelScope.async {
        getKeywordUseCase(Unit).let {
            if (it is Result.Success) {
                return@async it.data.keyword
            } else {
                return@async null
            }
        }
    }

    fun getUserNameAsync() = viewModelScope.launch {
        getUserUseCase(Unit).let {
            if (it is Result.Success) {
                _userName.postValue(it.data.name)
            }
        }
    }

    fun uploadDrawing() = viewModelScope.launch {
        val drawing = Drawing(
                userName = _userName.value ?: return@launch,
                keyword = getKeywordAsync().await() ?: return@launch,
                content = drawingContent.value ?: return@launch,
                imageUrl = drawingImageUrl.value ?: return@launch
        )
        uploadImageUseCase(drawing).let {
            if (it is Result.Success) {
               _isUploadSuccess.postValue(true)
            } else {
                _uploadErrorMessage.postValue((it as Result.Error).exception.message)
            }
        }
    }
}