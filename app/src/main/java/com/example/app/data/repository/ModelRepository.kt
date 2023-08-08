package com.example.app.data.repository

import com.example.app.models.SomeModel
import io.reactivex.rxjava3.core.Observable

interface ModelRepository {
    fun fetchModels(): Observable<List<SomeModel>>
}