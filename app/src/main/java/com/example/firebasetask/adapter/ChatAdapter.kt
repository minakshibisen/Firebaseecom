package com.example.firebasetask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.databinding.ItemChatLayoutBinding

class ChatAdapter(context: Context): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    var context: Context

    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
       return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
    class ViewHolder(binding: ItemChatLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemChatLayoutBinding

        init {
            this.binding = binding
        }
}
}