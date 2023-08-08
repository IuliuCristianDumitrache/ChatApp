package com.example.app.data.repository

import com.example.app.models.LoginResponse
import io.reactivex.rxjava3.core.Observable

interface LoginRepository {
    fun loginUser(email: String, password: String): Observable<LoginResponse>
}