package com.example.app.data

import androidx.room.*

@Dao
interface FriendDao {
    @Query("SELECT * FROM DEFAULT_MODEL")
    fun getAllFriends(): List<FriendEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: FriendEntity)
}