package com.example.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.app.data.AppDatabase
import com.example.app.data.ModelDao
import com.example.app.data.SessionManager
import com.example.app.data.remotedatasource.ModelRemoteDataSource
import com.example.app.data.remotedatasource.LoginRemoteDataSource
import com.example.app.data.remotedatasource.ModelLocalDataSource
import com.example.app.data.repository.LoginRepositoryImpl
import com.example.app.data.repository.ModelRepositoryImpl
import com.example.app.network.ApiFactory
import com.example.app.network.ApiService
import com.example.app.network.exceptions.NetworkExceptionHandler
import com.example.app.utils.StringResource
import com.example.app.utils.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.getClient()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "BLIX_DB")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideModelDao(appDatabase: AppDatabase): ModelDao {
        return appDatabase.modelDao()
    }

    @Provides
    fun provideStringResource(@ApplicationContext context: Context): StringResource {
        return StringResourceProvider(context)
    }

    @Provides
    fun provideNetworkExceptionHandler(stringResource: StringResource): NetworkExceptionHandler {
        return NetworkExceptionHandler(stringResource)
    }

    @Provides
    fun provideModelRemoteDataSource(
        apiService: ApiService
    ): ModelRemoteDataSource {
        return ModelRemoteDataSource(apiService)
    }

    @Provides
    fun provideLoginRemoteDataSource(
        apiService: ApiService
    ): LoginRemoteDataSource {
        return LoginRemoteDataSource(apiService)
    }

    @Provides
    fun provideModelLocalDataSource(
        modelDao: ModelDao
    ): ModelLocalDataSource {
        return ModelLocalDataSource(modelDao)
    }

    @Provides
    fun provideLoginRepository(loginRemoteDataSource: LoginRemoteDataSource, sessionManager: SessionManager): LoginRepositoryImpl {
        return LoginRepositoryImpl(loginRemoteDataSource, sessionManager)
    }

    @Provides
    fun provideModelRepository(modelRemoteDataSource: ModelRemoteDataSource, modelLocalDataSource: ModelLocalDataSource): ModelRepositoryImpl {
        return ModelRepositoryImpl(modelRemoteDataSource, modelLocalDataSource)
    }

    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(provideSharedPreferences(context))
    }

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "AppPreferences",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}