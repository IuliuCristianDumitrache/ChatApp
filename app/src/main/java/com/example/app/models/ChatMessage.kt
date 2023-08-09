package com.example.app.models

import com.example.app.data.ChatEntity

data class ChatMessage(
    val id: String,
    val friendId: String,
    val message: String,
    val name: String,
    val isSentByMe: Boolean,
    val date: Long
) {
    fun mapToEntity(): ChatEntity {
        return ChatEntity(
            name = this.name,
            friendId = this.friendId,
            message = this.message,
            sentByMe = this.isSentByMe,
            date = this.date
        )
    }
}
