package com.example.incwadimarket

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.incwadimarket.databinding.ItemChatBinding

class ChatAdapter(
    private val chatList: List<Chat>
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(
        val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {

        val binding = ItemChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int
    ) {

        val currentChat = chatList[position]

        holder.binding.txtName.text = currentChat.name
        holder.binding.txtLastMessage.text = currentChat.lastMessage
        holder.binding.txtTime.text = currentChat.time
        holder.binding.txtUnread.text = currentChat.unreadCount
        holder.binding.txtStatus.text = currentChat.status

        // OPEN CHAT SCREEN

        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                ChatScreen::class.java
            )

            intent.putExtra("name", currentChat.name)

            holder.itemView.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {

        return chatList.size

    }

}