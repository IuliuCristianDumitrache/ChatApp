package com.example.app.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ListItemBinding
import com.example.app.models.FriendModel

class FriendAdapter(private val listener: OnModelItemListener?) :
    ListAdapter<FriendModel, RecyclerView.ViewHolder>(FriendDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is FriendViewHolder -> {
                holder.bind(item, listener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (holder) {
            is FriendViewHolder -> {
                val item = currentList[position]
                if (payloads.isEmpty()) {
                    holder.bind(item, listener)
                    return
                }

                payloads.forEach {
                    val payloadList: List<FriendDiffCallback.ChangePayload> = it as List<FriendDiffCallback.ChangePayload>
                    payloadList.forEach { currentPayload ->
                        when (currentPayload) {
                            FriendDiffCallback.ChangePayload.NAME -> {
                                item.name?.let { name -> holder.updateName(name) }
                            }
                            FriendDiffCallback.ChangePayload.ALL -> {
                                holder.bind(item, listener)
                            }
                        }
                    }
                }
            }
        }
    }

    interface OnModelItemListener {
        fun onModelItemClicked(
            binding: ListItemBinding,
            model: FriendModel
        )
    }
}