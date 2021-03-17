package com.mut_jaeryo.givmkeyword.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.domain.common.Result
import com.mashup.domain.entities.User
import com.mashup.domain.repositories.PreferenceRepository
import com.mashup.domain.usecase.CreateUserUseCase
import com.mashup.domain.usecase.GetUserUseCase
import com.mut_jaeryo.givmkeyword.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
        private val getUserUseCase: GetUserUseCase,
        private val createUserUseCase: CreateUserUseCase,
        private val preferenceRepository: PreferenceRepository
) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    private val _todayWork = MutableLiveData<String>()
    val todayWork: LiveData<String> = _todayWork
    private val _userEditAvailable = MutableLiveData<Boolean>()
    val userEditAvailable: LiveData<Boolean> = _userEditAvailable
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: SingleLiveEvent<String> = _errorMessage

    fun loadUserName() = viewModelScope.launch {
        getUserUseCase(Unit).let {
            if (it is Result.Success) {
                _userName.value = it.data.name
                _userEditAvailable.value = it.data.name == "이름 미정"
            }
        }
    }

    fun loadTodayWork() = viewModelScope.launch {
        preferenceRepository.getWork().let {
            if (it is Result.Success) {
                _todayWork.value = "" + it.data
            }
        }
    }

    fun createUser(name: String) = viewModelScope.launch {
        createUserUseCase(User(name, 0)).let {
            if (it is Result.Success) {
                _userName.value = name
                _userEditAvailable.value = false
            } else if (it is Result.Error){
                _errorMessage.value = it.exception.message
            }
        }
    }
}