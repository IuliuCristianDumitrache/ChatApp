package com.example.app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FriendEntity::class, ChatEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun chatDao(): ChatDao

    companion object {
        //Migrations
    }
}