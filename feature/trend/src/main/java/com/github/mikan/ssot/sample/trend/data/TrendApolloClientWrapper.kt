package com.github.mikan.ssot.sample.trend.data

import com.apollographql.apollo.ApolloClient
import com.github.mikan.ssot.sample.core.network.RefreshTrigger
import com.github.mikan.ssot.sample.core.network.type.AddStarInput
import com.github.mikan.ssot.sample.core.network.type.RemoveStarInput
import com.github.mikan.ssot.sample.trend.AddStarMutation
import com.github.mikan.ssot.sample.trend.RemoveStarMutation
import com.github.mikan.ssot.sample.trend.TrendQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendApolloClientWrapper @Inject internal constructor(
    private val apolloClient: ApolloClient,
    private val refreshTrigger: RefreshTrigger,
) {
    fun fetchTrendData(): Flow<TrendQuery.Data> = flow {
        val data = apolloClient.query(TrendQuery()).execute().dataAssertNoErrors
        emit(data)

        refreshTrigger.refreshEvent.collect {
            val newData = apolloClient.query(TrendQuery()).execute().dataAssertNoErrors
            emit(newData)
        }
    }

    suspend fun addStar(repoId: String) {
        val mutation = AddStarMutation(AddStarInput(starrableId = repoId))
        apolloClient.mutation(mutation).execute()
        refreshTrigger.refresh()
    }

    suspend fun removeStar(repoId: String) {
        val mutation = RemoveStarMutation(RemoveStarInput(starrableId = repoId))
        apolloClient.mutation(mutation).execute()
        refreshTrigger.refresh()
    }
}
