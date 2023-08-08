package com.example.app.data.repository

import com.example.app.data.SessionManager
import com.example.app.models.LoginResponse
import com.example.app.data.remotedatasource.LoginRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val sessionManager: SessionManager
) : LoginRepository {
    override fun loginUser(email: String, password: String): Observable<LoginResponse> =
        loginRemoteDataSource.loginUser(email, password).doOnNext {
            sessionManager.setAccessToken(it.accessToken)
        }
}