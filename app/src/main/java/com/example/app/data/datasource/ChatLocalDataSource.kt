package com.example.app.data.datasource

import com.example.app.data.ChatDao
import com.example.app.models.ChatMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatLocalDataSource @Inject constructor(
    private val dao: ChatDao
) {
    fun fetchMessages(friendId: String): List<ChatMessage> {
        return dao.getMessages(friendId).map {
            it.toModel()
        }
    }
    fun insertMessage(model: ChatMessage) {
        dao.insert(model.mapToEntity())
    }
    fun insertMessages(models: List<ChatMessage>) {
        models.forEach { model ->
            dao.insert(model.mapToEntity())
        }
    }

    fun getLastMessageByFriendId(id: String): ChatMessage? {
        return dao.getLastMessageByFriendId(id)?.toModel()
    }
}