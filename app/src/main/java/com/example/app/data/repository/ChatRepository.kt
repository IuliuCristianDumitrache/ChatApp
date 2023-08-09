package com.example.app.data.repository

import com.example.app.models.ChatMessage

interface ChatRepository {
    fun fetchMessages(friendId: String): List<ChatMessage>

    fun addMessage(message: ChatMessage)
}