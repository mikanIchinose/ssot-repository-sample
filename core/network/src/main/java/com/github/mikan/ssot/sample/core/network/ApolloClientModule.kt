package com.github.mikan.ssot.sample.core.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApolloClientModule {
    private const val BASE_URL = "https://api.github.com/graphql"

    @Singleton
    @Provides
    fun provideApolloClient(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ) = ApolloClient.Builder()
        .okHttpClient(okHttpClient)
        .serverUrl(BASE_URL)
        .addHttpHeader("Authorization", "Bearer ${BuildConfig.GITHUB_PAT}")
        .addInterceptor(ApolloNetworkConnectivityInterceptor(context))
        .build()

    @Singleton
    @Provides
    fun provideOkhttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}