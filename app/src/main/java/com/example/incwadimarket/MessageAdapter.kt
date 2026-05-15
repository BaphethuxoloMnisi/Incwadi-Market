package com.example.incwadimarket

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.incwadimarket.databinding.ItemMessageBinding

class MessageAdapter(

    private val messageList: List<Message>,
    private val currentUser: String

) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(
        val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {

        val binding =
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MessageViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {

        val currentMessage =
            messageList[position]

        holder.binding.txtMessage.text =
            currentMessage.message

        holder.binding.txtTime.text =
            "${currentMessage.time} ✓✓"

        // SENT OR RECEIVED

        if (currentMessage.sender == currentUser) {

            holder.binding.messageContainer.gravity =
                Gravity.END

        } else {

            holder.binding.messageContainer.gravity =
                Gravity.START

        }

    }

    override fun getItemCount(): Int {

        return messageList.size

    }

}