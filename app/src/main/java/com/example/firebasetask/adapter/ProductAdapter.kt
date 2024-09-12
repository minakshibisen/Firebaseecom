package com.example.firebasetask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasetask.R
import com.example.firebasetask.activity.ProductDetailActivity
import com.example.firebasetask.databinding.ItemProductLayoutBinding
import com.example.firebasetask.model.ProductDataModel

class ProductAdapter(
    private val product: List<ProductDataModel.Data.Product>, context: Context
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = product[position]

        holder.binding.textFruitCount.text = current.quantity
        holder.binding.textProductName.text = current.name
        holder.binding.textProductPrice.text = current.price
        Glide.with(context)
            .load(current.image)
            .placeholder(R.drawable.loading)
            .into(holder.binding.viewFruit)
        holder.binding.root.setOnClickListener {
            context.startActivity(Intent(context, ProductDetailActivity::class.java))
        }

    }
    override fun getItemCount(): Int {
        return product.size
    }

    class ViewHolder(binding: ItemProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemProductLayoutBinding

        init {
            this.binding = binding
        }
    }

}