package com.example.app.ui.friends

import androidx.recyclerview.widget.DiffUtil
import com.example.app.models.FriendModel

class FriendDiffCallback : DiffUtil.ItemCallback<FriendModel>() {

    enum class ChangePayload {
        ALL, NAME
    }

    override fun areItemsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FriendModel, newItem: FriendModel): Boolean {
        return when {
            oldItem.id != newItem.id -> { false }
            oldItem.name != newItem.name -> { false }
            else -> true
        }
    }

    override fun getChangePayload(oldItem: FriendModel, newItem: FriendModel): List<ChangePayload> {
        val changePayloadList = mutableListOf<ChangePayload>()

        val shouldRefreshOnlyName = oldItem.name != newItem.name

        when {
            shouldRefreshOnlyName -> {
                changePayloadList.add(ChangePayload.NAME)
            }
            else -> {
                changePayloadList.add(ChangePayload.ALL)
            }
        }

        return changePayloadList.toList()
    }
}
