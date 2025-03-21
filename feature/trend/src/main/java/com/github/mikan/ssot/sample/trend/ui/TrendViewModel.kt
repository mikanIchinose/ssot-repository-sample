package com.github.mikan.ssot.sample.trend.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikan.ssot.sample.trend.TrendQuery
import com.github.mikan.ssot.sample.trend.data.TrendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TrendViewModel @Inject constructor(
    private val trendRepository: TrendRepository
) : ViewModel() {
    val uiState = trendRepository.fetchTrendData()
        .map { TrendUiState(it, null) }
        .catch { emit(TrendUiState(null, Error(it))) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrendUiState.Initial
        )

    suspend fun addStar(repoId: String) {
        trendRepository.addStar(repoId)
    }

    suspend fun removeStar(repoId: String) {
        trendRepository.removeStar(repoId)
    }

    suspend fun refresh() {
        trendRepository.refresh()
    }
}

data class TrendUiState(
    val content: TrendQuery.Data?,
    val error: Error?,
) {
    companion object {
        val Initial = TrendUiState(null, null)
    }
}