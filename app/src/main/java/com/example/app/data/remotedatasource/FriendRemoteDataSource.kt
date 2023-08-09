package com.example.app.data.remotedatasource

import com.example.app.models.FriendModel
import com.example.app.network.ApiService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

//TODO - from when there will be a server
@Singleton
class FriendRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun fetchFriends(): Observable<List<FriendModel>> =
        apiService.getModels()
}