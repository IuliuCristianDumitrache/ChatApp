package com.example.app.data

import androidx.room.*

@Dao
interface ModelDao {
    @Query("SELECT * FROM DEFAULT_MODEL")
    suspend fun getAllModels(): List<ModelEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: ModelEntity)
}