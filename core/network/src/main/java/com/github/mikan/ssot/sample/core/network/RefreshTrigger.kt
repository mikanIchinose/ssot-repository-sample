package com.github.mikan.ssot.sample.core.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface RefreshTrigger {
    val refreshEvent: Flow<Unit>
    suspend fun refresh()
}

internal class DefaultRefreshTrigger @Inject constructor() : RefreshTrigger {
    private val _refreshEvent = MutableSharedFlow<Unit>()
    override val refreshEvent: Flow<Unit> = _refreshEvent.asSharedFlow()
    override suspend fun refresh() {
        _refreshEvent.emit(Unit)
    }
}

@InstallIn(SingletonComponent::class)
@Module
internal interface RefreshTriggerModule {
    @Binds
    fun bindRefreshTrigger(impl: DefaultRefreshTrigger): RefreshTrigger
}