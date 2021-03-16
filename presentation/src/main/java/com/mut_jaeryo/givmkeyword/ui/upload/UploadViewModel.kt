package com.mut_jaeryo.givmkeyword.ui.upload

import androidx.lifecycle.*
import com.mashup.domain.common.Result
import com.mashup.domain.entities.Drawing
import com.mashup.domain.usecase.UploadUseCase
import com.mut_jaeryo.givmkeyword.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
        private val uploadUseCase: UploadUseCase,
        private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    //TODO: userName과 keyword를 가져오는 UseCase 생성
    private val _userName = savedStateHandle.getLiveData<String>("userName")
    val userName: LiveData<String> = _userName
    private val _drawingImageUrl = savedStateHandle.getLiveData<String>("imageUrl")
    val drawingImageUrl: LiveData<String> = _drawingImageUrl
    private val drawingKeyword = savedStateHandle.getLiveData<String>("keyword")

    val drawingContent = MutableLiveData<String>()
    private val _isUploading = SingleLiveEvent<Boolean>()
    val isUploading: SingleLiveEvent<Boolean> = _isUploading
    private val _isUploadSuccess = SingleLiveEvent<Boolean>()
    val isUploadSuccess: SingleLiveEvent<Boolean> = _isUploadSuccess
    private val _uploadErrorMessage = SingleLiveEvent<String>()
    val uploadErrorMessage: SingleLiveEvent<String> = _uploadErrorMessage

    fun uploadDrawing() = viewModelScope.launch {
        val drawing = Drawing(
                userName = userName.value ?: return@launch,
                keyword = drawingKeyword.value ?: return@launch,
                content = drawingContent.value ?: return@launch,
                imageUrl = drawingImageUrl.value ?: return@launch
        )
        uploadUseCase(drawing).let {
            if (it is Result.Success) {
               _isUploadSuccess.value = true
            } else {
                _uploadErrorMessage.value = (it as Result.Error).exception.message
            }
        }
    }
}