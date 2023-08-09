package com.example.app.ui.friends

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ListItemBinding
import com.example.app.models.FriendModel

class FriendViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: FriendModel, listener: FriendAdapter.OnModelItemListener?) {
        ViewCompat.setTransitionName(binding.tvName, "${model.id}${model.name}")
        ViewCompat.setTransitionName(binding.root, "${model.id}")

        binding.tvName.text = model.name

        binding.root.setOnClickListener {
            listener?.onModelItemClicked(binding, model)
        }
    }

    fun updateName(name: String) {
        binding.tvName.text = name
    }

    fun updateDescription(description: String) {
        binding.tvDescription.text = description
    }
}