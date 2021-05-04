package com.mut_jaeryo.presentation.ui.version

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.usecase.GetAppVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersionViewModel @Inject constructor(
        private val getAppVersionUseCase: GetAppVersionUseCase
): ViewModel() {
    private val _isNewVersion = MutableLiveData(true)
    val isNewVersion: LiveData<Boolean> = _isNewVersion

    fun checkAppVersion(currentVersion: String) = viewModelScope.launch {
        getAppVersionUseCase(Unit).let {
            if (it is Result.Success) {
                _isNewVersion.postValue(currentVersion == it.data)
            } else if (it is Result.Error){
                Log.e("VersionViewModel", it.exception.stackTraceToString())
                _isNewVersion.postValue(false)
            }
        }
    }
}