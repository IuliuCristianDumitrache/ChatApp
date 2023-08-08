package com.example.app.ui.models

import androidx.recyclerview.widget.DiffUtil
import com.example.app.models.SomeModel

class ModelDiffCallback : DiffUtil.ItemCallback<SomeModel>() {

    enum class ChangePayload {
        ALL, NAME
    }

    override fun areItemsTheSame(oldItem: SomeModel, newItem: SomeModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SomeModel, newItem: SomeModel): Boolean {
        return when {
            oldItem.id != newItem.id -> { false }
            oldItem.name != newItem.name -> { false }
            else -> true
        }
    }

    override fun getChangePayload(oldItem: SomeModel, newItem: SomeModel): List<ChangePayload> {
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
