package com.mut_jaeryo.presentation.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.usecase.CreateUserUseCase
import com.mut_jaeryo.domain.usecase.GetUserUseCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
        private val getUserUseCase: GetUserUseCase,
        private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    private val _userEditAvailable = MutableLiveData<Boolean>()
    val userEditAvailable: LiveData<Boolean> = _userEditAvailable
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: SingleLiveEvent<String> = _errorMessage

    init {
        loadUserName()
    }

    private fun loadUserName() = viewModelScope.launch {
        getUserUseCase(Unit).let {
            if (it is Result.Success) {
                _userName.value = it.data?.name
                _userEditAvailable.value = it.data?.name == null
            }
        }
    }

    fun createUser(name: String) = viewModelScope.launch {
        createUserUseCase(User(name, 0)).let {
            if (it is Result.Success) {
                _userName.value = name
                _userEditAvailable.value = false
            } else if (it is Result.Error) {
                it.exception.message?.let { message ->
                    _errorMessage.postValue(message)
                }
            }
        }
    }
}