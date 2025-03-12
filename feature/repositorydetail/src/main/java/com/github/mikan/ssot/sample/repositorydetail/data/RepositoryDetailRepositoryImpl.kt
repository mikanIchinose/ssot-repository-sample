package com.github.mikan.ssot.sample.repositorydetail.data

import com.apollographql.apollo.ApolloClient
import com.github.mikan.ssot.sample.core.network.RefreshTrigger
import com.github.mikan.ssot.sample.core.network.type.AddStarInput
import com.github.mikan.ssot.sample.core.network.type.RemoveStarInput
import com.github.mikan.ssot.sample.repositorydetail.AddStarMutation
import com.github.mikan.ssot.sample.repositorydetail.RemoveStarMutation
import com.github.mikan.ssot.sample.repositorydetail.RepositoryDetailQuery
import com.github.mikan.ssot.sample.repositorydetail.domain.RepositoryDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RepositoryDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val refreshTrigger: RefreshTrigger,
) : RepositoryDetailRepository {
    override fun getRepositoryDetail(
        owner: String,
        name: String,
    ): Flow<RepositoryDetailQuery.Data> = flow {
        val query = RepositoryDetailQuery(owner, name)
        val data = apolloClient.query(query).execute().dataAssertNoErrors
        emit(data)

        refreshTrigger.refreshEvent.collect {
            val newData = apolloClient.query(query).execute().dataAssertNoErrors
            emit(newData)
        }
    }

    override suspend fun refresh() {
        refreshTrigger.refresh()
    }

    override suspend fun addStar(id: String) {
        apolloClient.mutation(AddStarMutation(AddStarInput(starrableId = id))).execute()
        refreshTrigger.refresh()
    }

    override suspend fun removeStar(id: String) {
        apolloClient.mutation(RemoveStarMutation(RemoveStarInput(starrableId = id))).execute()
        refreshTrigger.refresh()
    }
}
