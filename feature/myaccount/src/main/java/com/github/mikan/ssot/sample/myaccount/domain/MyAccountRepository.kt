package com.github.mikan.ssot.sample.myaccount.domain

import com.github.mikan.ssot.sample.myaccount.MyAccountQuery
import kotlinx.coroutines.flow.Flow

interface MyAccountRepository {
    fun fetchMyAccountAndObserve(): Flow<MyAccountQuery.Data>
    suspend fun refresh()
}