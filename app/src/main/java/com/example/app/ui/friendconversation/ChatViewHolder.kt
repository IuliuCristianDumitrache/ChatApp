package com.example.app.ui.friendconversation

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.ChatItemBinding
import com.example.app.models.ChatMessage
import java.text.DateFormat
import java.util.Date

class ChatViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ChatMessage) {

        binding.friendLayout.isVisible = false
        binding.myLayout.isVisible = false

        if (item.isSentByMe) {
            binding.myLayout.isVisible = true
            binding.myName.text = binding.myLayout.context.getString(R.string.me)
            binding.myMessage.text = item.message
            val date = Date()
            date.time = item.date
            binding.myMessageDate.text =  DateFormat.getDateTimeInstance().format(date)
        } else {
            binding.friendLayout.isVisible = true
            binding.friendName.text = item.name
            binding.friendMessage.text = item.message
            val date = Date()
            date.time = item.date
            binding.friendMessageDate.text = DateFormat.getDateTimeInstance().format(date)
        }
    }
}