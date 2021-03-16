package com.mut_jaeryo.givmkeyword.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl
}