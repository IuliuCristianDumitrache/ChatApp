package com.example.app.data.repository

import com.example.app.models.FriendModel

interface FriendRepository {
    fun fetchFriends(): List<FriendModel>
    fun addFriend(friendModel: FriendModel)
}