package com.github.mikan.ssot.sample.repositorydetail.domain

import com.github.mikan.ssot.sample.repositorydetail.RepositoryDetailQuery
import kotlinx.coroutines.flow.Flow

interface RepositoryDetailRepository {
    fun getRepositoryDetail(owner: String, name: String): Flow<RepositoryDetailQuery.Data>
    suspend fun refresh()
    suspend fun addStar(id: String)
    suspend fun removeStar(id: String)
}
