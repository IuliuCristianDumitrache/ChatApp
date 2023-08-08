package com.example.app.data.remotedatasource

import com.example.app.models.SomeModel
import com.example.app.network.ApiService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchModels(): Observable<List<SomeModel>> =
        apiService.getModels()
}