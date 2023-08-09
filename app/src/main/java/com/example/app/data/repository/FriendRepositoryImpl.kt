package com.example.app.data.repository

import com.example.app.data.remotedatasource.FriendLocalDataSource
import com.example.app.data.remotedatasource.FriendRemoteDataSource
import com.example.app.models.FriendModel
import javax.inject.Inject
import javax.inject.Singleton

//TODO - future improvement if there will be a back-end
@Singleton
class FriendRepositoryImpl @Inject constructor(
    private val friendRemoteDataSource: FriendRemoteDataSource,
    private val friendLocalDataSource: FriendLocalDataSource
) : FriendRepository {
    override fun fetchFriends(): List<FriendModel> =
        friendLocalDataSource.fetchFriends()

    override fun addFriend(friendModel: FriendModel) {
        friendLocalDataSource.insertModel(friendModel)
    }
}