package com.example.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.app.models.FriendModel

@Entity(tableName = "DEFAULT_MODEL", indices = [Index(value = ["ID"], unique = true)])
data class FriendEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "ID")
    var id: String = "",

    @ColumnInfo(name = "NAME")
    var name: String = "",
) {
    fun toModel(): FriendModel {
        return FriendModel(id = id, name = name)
    }
}