package com.github.mikan.ssot.sample.myaccount.data

import com.apollographql.apollo.ApolloClient
import com.github.mikan.ssot.sample.myaccount.MyAccountQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MyAccountApolloClientWrapper @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun fetchMyAccountData(): MyAccountQuery.Data {
        return apolloClient.query(MyAccountQuery()).execute().dataAssertNoErrors
    }
}
