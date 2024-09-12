package com.example.firebasetask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.databinding.ExclusiveItemLayoutBinding
import com.example.firebasetask.model.ProductDataModel

class AddAdapter(var data: List<ProductDataModel.Data>, context: Context?) :
    RecyclerView.Adapter<AddAdapter.ViewHolder>() {

    lateinit var context: Context

    init {
        if (context != null) {
            this.context = context
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExclusiveItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.binding.textTitle.text = current.title.toString()
        holder.binding.textViewAll.text = current.description.toString()
        holder.binding.productRecyclerview.adapter = ProductAdapter(current.productList, context)
        holder.binding.productRecyclerview.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
    }

    class ViewHolder(val binding: ExclusiveItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}