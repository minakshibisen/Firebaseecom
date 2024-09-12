package com.example.firebasetask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasetask.R
import com.example.firebasetask.databinding.ItemPrductCartLayoutBinding
import com.example.firebasetask.model.ProductCartModel
import com.example.firebasetask.model.ProductDataModel

class ProductCartAdapter (var data: List<ProductCartModel>,context: Context): RecyclerView.Adapter<ProductCartAdapter.ViewHolder>() {
    var context: Context
    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPrductCartLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.binding.textDescription.text=current.description
        holder.binding.textColor.text=current.color
        holder.binding.textProductName.text=current.name
        holder.binding.textSize.text=current.size
        holder.binding.textPrice.text = current.price.toString()
        Glide.with(context)
            .load(current.image)
            .placeholder(R.drawable.loading)
            .into(holder.binding.imgProduct)
        var count = 1
        holder.binding.imgClose.setOnClickListener {
        }
        holder.binding.textCount.text = count.toString()

        holder.binding.imgRemove.setOnClickListener {
            if (count > 1) {
                count--
                holder.binding.textCount.text = count.toString()
            } else {
                Toast.makeText(context, "Minimum quantity reached", Toast.LENGTH_SHORT).show()
            }
        }
       holder.binding.imageAdd.setOnClickListener {
            count++
            holder.binding.textCount.text = count.toString()
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(binding: ItemPrductCartLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemPrductCartLayoutBinding
        init {
            this.binding = binding
        }
}}