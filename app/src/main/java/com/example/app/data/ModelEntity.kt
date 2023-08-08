package com.example.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.app.models.SomeModel

@Entity(tableName = "DEFAULT_MODEL", indices = [Index(value = ["ID"], unique = true)])
data class ModelEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "ID")
    var id: String = "",

    @ColumnInfo(name = "NAME")
    var name: String = "",
) {
    fun toModel(): SomeModel {
        return SomeModel(id = id, name = name)
    }
}