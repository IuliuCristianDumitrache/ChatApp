package com.example.app.ui.friendconversation

import androidx.recyclerview.widget.DiffUtil
import com.example.app.models.ChatMessage

class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {

    enum class ChangePayload {
        ALL
    }

    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return when {
            oldItem.friendId != newItem.friendId -> { false }
            oldItem.message != newItem.message -> { false }
            oldItem.id != newItem.id -> { false }
            else -> true
        }
    }

    override fun getChangePayload(oldItem: ChatMessage, newItem: ChatMessage): List<ChangePayload> {
        val changePayloadList = mutableListOf<ChangePayload>()
        changePayloadList.add(ChangePayload.ALL)
        return changePayloadList.toList()
    }
}
