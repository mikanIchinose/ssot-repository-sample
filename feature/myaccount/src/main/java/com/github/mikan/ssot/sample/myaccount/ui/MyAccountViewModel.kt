package com.github.mikan.ssot.sample.myaccount.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikan.ssot.sample.myaccount.MyAccountQuery
import com.github.mikan.ssot.sample.myaccount.domain.MyAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val myAccountRepository: MyAccountRepository,
) : ViewModel() {
    val uiState = myAccountRepository.fetchMyAccountAndObserve()
        .map { MyAccountUiState(it, null) }
        .catch { emit(MyAccountUiState(null, Error(it))) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MyAccountUiState.Initial
        )

    suspend fun refresh() {
        myAccountRepository.refresh()
    }
}

data class MyAccountUiState(
    val content: MyAccountQuery.Data?,
    val error: Error?,
) {
    companion object {
        val Initial = MyAccountUiState(null, null)
    }
}