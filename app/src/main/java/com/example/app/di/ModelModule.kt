package com.example.app.di

import com.example.app.data.repository.ModelRepository
import com.example.app.data.repository.ModelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ModelModule {
    @Binds
    abstract fun bindModelRepository(modelRepositoryImpl: ModelRepositoryImpl): ModelRepository
}