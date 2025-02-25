package com.github.mikan.ssot.sample

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(count = 0))
    val uiState = _uiState.asStateFlow()

    fun increment() {
        _uiState.update {
            it.copy(count = it.count + 1)
        }
    }

    fun decrement() {
        _uiState.update {
            it.copy(count = it.count - 1)
        }
    }
}

data class HomeUiState(
    val count: Int
) {
    val decrementEnabled = count > 0
    val incrementEnabled = count < 10
}