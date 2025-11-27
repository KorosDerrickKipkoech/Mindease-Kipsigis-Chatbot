package com.mindease.kipsigis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindease.kipsigis.databinding.ItemUserBinding
import com.mindease.kipsigis.databinding.ItemBotBinding

class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val USER = 1
    private val BOT = 2

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) USER else BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == USER) {
            val binding = ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            UserViewHolder(binding)
        } else {
            val binding = ItemBotBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            BotViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        if (holder is UserViewHolder) holder.bind(msg)
        else if (holder is BotViewHolder) holder.bind(msg)
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: ChatMessage) {
            binding.userMessage.text = msg.message
        }
    }

    class BotViewHolder(private val binding: ItemBotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: ChatMessage) {
            binding.botMessage.text = msg.message
        }
    }
}
