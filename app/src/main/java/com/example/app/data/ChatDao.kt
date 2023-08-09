package com.example.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ChatDao {
    @Query("SELECT * FROM CHAT_MESSAGE_ENTITY WHERE FRIEND_ID = :friendId")
    fun getMessages(friendId: String): List<ChatEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: ChatEntity)
}