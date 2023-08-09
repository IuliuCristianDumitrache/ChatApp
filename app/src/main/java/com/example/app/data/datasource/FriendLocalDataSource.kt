package com.example.app.data.datasource

import com.example.app.data.FriendDao
import com.example.app.models.FriendModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendLocalDataSource @Inject constructor(
    private val dao: FriendDao
) {
    fun fetchFriends(): List<FriendModel> {
        return dao.getAllFriends().map {
            it.toModel()
        }
    }
    fun insertModel(model: FriendModel) {
        dao.insert(model.mapToEntity())
    }
    fun insertModels(models: List<FriendModel>) {
        models.forEach { model ->
            dao.insert(model.mapToEntity())
        }
    }
}