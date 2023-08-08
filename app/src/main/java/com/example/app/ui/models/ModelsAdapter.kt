package com.example.app.ui.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ListItemBinding
import com.example.app.models.SomeModel

class ModelsAdapter(private val listener: OnModelItemListener?) :
    ListAdapter<SomeModel, RecyclerView.ViewHolder>(ModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return ModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when (holder) {
            is ModelViewHolder -> {
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
            is ModelViewHolder -> {
                val item = currentList[position]
                if (payloads.isEmpty()) {
                    holder.bind(item, listener)
                    return
                }

                payloads.forEach {
                    val payloadList: List<ModelDiffCallback.ChangePayload> = it as List<ModelDiffCallback.ChangePayload>
                    payloadList.forEach { currentPayload ->
                        when (currentPayload) {
                            ModelDiffCallback.ChangePayload.NAME -> {
                                item.name?.let { name -> holder.updateName(name) }
                            }
                            ModelDiffCallback.ChangePayload.ALL -> {
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
            model: SomeModel
        )
    }
}