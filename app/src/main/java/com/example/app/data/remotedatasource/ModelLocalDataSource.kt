package com.example.app.data.remotedatasource

import com.example.app.data.ModelDao
import com.example.app.models.SomeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelLocalDataSource @Inject constructor(
    private val dao: ModelDao
) {
    fun insertModels(models: List<SomeModel>) {
        models.forEach { model ->
            dao.insert(model.mapToEntity())
        }
    }
}