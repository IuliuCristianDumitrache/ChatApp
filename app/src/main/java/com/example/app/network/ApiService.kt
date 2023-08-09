package com.example.app.network

import com.example.app.models.FriendModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers(
        "Content-Type: application/json")
    @GET("breeds")
    fun getModels(): Observable<List<FriendModel>>
}