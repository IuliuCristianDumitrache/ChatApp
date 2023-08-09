package com.example.app.data.repository

import com.example.app.data.datasource.ChatLocalDataSource
import com.example.app.models.ChatMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val chatLocalDataSource: ChatLocalDataSource
) : ChatRepository {
    override fun fetchMessages(friendId: String): List<ChatMessage> {
        return chatLocalDataSource.fetchMessages(friendId)
    }

    override fun addMessage(message: ChatMessage) {
        chatLocalDataSource.insertMessage(message)
    }

    override fun getLastMessageByFriendId(id: String): ChatMessage? {
        return chatLocalDataSource.getLastMessageByFriendId(id)
    }

}