package com.github.mikan.ssot.sample.repositorydetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.mikan.ssot.sample.repositorydetail.RepositoryDetailQuery
import com.github.mikan.ssot.sample.repositorydetail.domain.RepositoryDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val repository: RepositoryDetailRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<RepositoryDetailRoute>()
    val uiState = repository.getRepositoryDetail(route.owner, route.name)
        .map { RepositoryDetailUiState(it, null) }
        .catch { emit(RepositoryDetailUiState(null, Error(it))) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RepositoryDetailUiState.Initial,
        )

    fun addStar(id: String) {
        viewModelScope.launch {
            repository.addStar(id)
        }
    }

    fun removeStar(id: String) {
        viewModelScope.launch {
            repository.removeStar(id)
        }
    }
}

data class RepositoryDetailUiState(
    val content: RepositoryDetailQuery.Data?,
    val error: Error?,
) {
    companion object {
        val Initial = RepositoryDetailUiState(null, null)
    }
}
