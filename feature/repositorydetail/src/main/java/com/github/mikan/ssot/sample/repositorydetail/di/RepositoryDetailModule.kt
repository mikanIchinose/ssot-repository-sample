package com.github.mikan.ssot.sample.repositorydetail.di

import com.github.mikan.ssot.sample.repositorydetail.data.RepositoryDetailRepositoryImpl
import com.github.mikan.ssot.sample.repositorydetail.domain.RepositoryDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface RepositoryDetailModule {
    @Binds
    fun bindRepositoryDetailRepository(impl: RepositoryDetailRepositoryImpl): RepositoryDetailRepository
}