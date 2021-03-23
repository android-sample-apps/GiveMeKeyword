package com.mut_jaeryo.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _imageUrl = SingleLiveEvent<String>()
    val imageUrl: SingleLiveEvent<String> = _imageUrl

    fun setImageUrl(imageUrl: String) {
        _imageUrl.value = imageUrl
    }
}