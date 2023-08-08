package com.example.app.data.repository

import com.example.app.data.remotedatasource.ModelLocalDataSource
import com.example.app.data.remotedatasource.ModelRemoteDataSource
import com.example.app.models.SomeModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepositoryImpl @Inject constructor(
    private val modelRemoteDataSource: ModelRemoteDataSource,
    private val modelLocalDataSource: ModelLocalDataSource
) : ModelRepository {
    override fun fetchModels(): Observable<List<SomeModel>> =
        modelRemoteDataSource.fetchModels().doOnNext {
            modelLocalDataSource.insertModels(it)
        }
}