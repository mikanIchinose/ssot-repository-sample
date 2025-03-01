package com.github.mikan.ssot.sample.myaccount.data

import com.github.mikan.ssot.sample.core.network.RefreshTrigger
import com.github.mikan.ssot.sample.myaccount.MyAccountQuery
import com.github.mikan.ssot.sample.myaccount.domain.MyAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MyAccountRepositoryImpl @Inject constructor(
    private val apolloClient: MyAccountApolloClientWrapper,
    private val refreshTrigger: RefreshTrigger,
) : MyAccountRepository {
    override fun fetchMyAccountAndObserve(): Flow<MyAccountQuery.Data> = flow {
        val data = apolloClient.fetchMyAccountData()
        emit(data)

        refreshTrigger.refreshEvent.collect {
            val newData = apolloClient.fetchMyAccountData()
            emit(newData)
        }
    }

    override suspend fun refresh() {
        refreshTrigger.refresh()
    }
}