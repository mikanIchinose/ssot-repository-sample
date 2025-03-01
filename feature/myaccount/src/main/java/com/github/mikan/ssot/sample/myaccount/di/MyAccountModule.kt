package com.github.mikan.ssot.sample.myaccount.di

import com.github.mikan.ssot.sample.myaccount.data.MyAccountRepositoryImpl
import com.github.mikan.ssot.sample.myaccount.domain.MyAccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface MyAccountModule {
    @Binds
    fun bindMyAccountRepository(impl: MyAccountRepositoryImpl): MyAccountRepository
}