package com.example.app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [(FriendEntity::class)],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun modelDao(): FriendDao

    companion object {
        //Migrations
    }
}