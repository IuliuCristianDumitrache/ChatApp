package com.example.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.app.models.ChatMessage

@Entity(tableName = "CHAT_MESSAGE_ENTITY", indices = [Index(value = ["DATE"], unique = true)])
data class ChatEntity (
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,

    @ColumnInfo(name = "NAME")
    var name: String = "",

    @ColumnInfo(name = "FRIEND_ID")
    var friendId: String = "",

    @ColumnInfo(name = "MESSAGE")
    var message: String = "",

    @ColumnInfo(name = "IS_SENT_BY_ME")
    var sentByMe: Boolean = false,

    @ColumnInfo(name = "DATE")
    var date: Long = 0L,
) {
    fun toModel(): ChatMessage {
        return ChatMessage(
            id = uid.toString(),
            friendId = friendId,
            message = message,
            name = name,
            isSentByMe = sentByMe,
            date = date
        )
    }
}