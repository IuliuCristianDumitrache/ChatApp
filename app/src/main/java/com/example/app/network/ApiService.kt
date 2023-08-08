package com.example.app.network

import com.example.app.models.SomeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers(
        "Content-Type: application/json",
        "x-api-key: live_Db5dUNQjx3Yu1vsTxgjbFFjQdinxU2vwIkkTkjq6sFpVmBKr9Ar4fQaZb7L3JMYU")
    @GET("breeds")
    fun getModels(): Observable<List<SomeModel>>
}