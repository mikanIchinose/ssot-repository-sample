package com.github.mikan.ssot.sample.core.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow

class ApolloNetworkConnectivityInterceptor(private val context: Context) : ApolloInterceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        if (context.isNetworkConnected()) {
            return chain.proceed(request)
        } else {
            throw NoNetworkException()
        }
    }
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
private fun Context.isNetworkConnected(): Boolean =
    getSystemService(ConnectivityManager::class.java)?.activeNetwork != null
