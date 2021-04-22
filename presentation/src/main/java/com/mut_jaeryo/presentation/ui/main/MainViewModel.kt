package com.mut_jaeryo.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mut_jaeryo.domain.common.Result
import com.mut_jaeryo.domain.entities.Keyword
import com.mut_jaeryo.domain.usecase.GetDateUseCase
import com.mut_jaeryo.domain.usecase.SetDateUseCase
import com.mut_jaeryo.domain.usecase.SetRequestCountCase
import com.mut_jaeryo.presentation.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val getDateUseCase: GetDateUseCase,
        private val setDateUseCase: SetDateUseCase,
        private val setRequestCountCase: SetRequestCountCase
) : ViewModel() {
    private val _imageUrl = SingleLiveEvent<String>()
    val imageUrl: SingleLiveEvent<String> = _imageUrl

    companion object {
        const val RESET_DAY_DIFF = 1
        const val RESET_REQUEST_COUNT = 0
    }

    fun setImageUrl(imageUrl: String) {
        _imageUrl.value = imageUrl
    }

    fun requestResetKeywordRequestCount() = viewModelScope.launch {
        getDateUseCase(Unit).let {
            if (it is Result.Success) {
                it.data?.let { date ->
                    if (getDaysDiffUserInfoDate(date) >= RESET_DAY_DIFF) {
                        setRequestCount()
                        setUserDate()
                    }
                }
            }
        }
    }

    private fun setRequestCount() = viewModelScope.launch {
        setRequestCountCase(RESET_REQUEST_COUNT)
    }

    private fun setUserDate() = viewModelScope.launch {
        val currentDate = GregorianCalendar()
        currentDate.add(Calendar.DAY_OF_MONTH, 1)
        currentDate.set(Calendar.HOUR_OF_DAY, 8)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MINUTE, 0)

        val month = currentDate[Calendar.MONTH] + 1

        val date = "${currentDate[Calendar.YEAR]}-$month-${currentDate[Calendar.DAY_OF_MONTH]}"
        setDateUseCase(date)
    }

    private fun getDaysDiffUserInfoDate(userDate: String): Int {
        val previousDate = GregorianCalendar()
        val date = userDate.split("-")

        val year: Int = date[0].toInt()
        val month: Int = date[1].toInt() - 1
        val day = date[2].toInt()
        previousDate.set(Calendar.YEAR, year)
        previousDate.set(Calendar.MONTH, month)
        previousDate.set(Calendar.DAY_OF_MONTH, day)
        previousDate.set(Calendar.HOUR_OF_DAY, 8)
        previousDate.set(Calendar.MINUTE, 0)
        previousDate.set(Calendar.SECOND, 0)

        val diff: Long = previousDate.timeInMillis - GregorianCalendar().timeInMillis
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days.toInt()
    }
}