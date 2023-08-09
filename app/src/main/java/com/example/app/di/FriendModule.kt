package com.example.app.di

import com.example.app.data.repository.FriendRepository
import com.example.app.data.repository.FriendRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FriendModule {
    @Binds
    abstract fun bindModelRepository(modelRepositoryImpl: FriendRepositoryImpl): FriendRepository
}