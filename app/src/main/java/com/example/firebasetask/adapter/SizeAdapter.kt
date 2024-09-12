package com.example.firebasetask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetask.activity.ProductDetailActivity
import com.example.firebasetask.databinding.ItemSizeLayoutBinding

class SizeAdapter(
                  private val list: ArrayList<String>,
                  private val onClickSize: ProductDetailActivity
): RecyclerView.Adapter<SizeAdapter.ViewHolder>() {


    private  var selectedItem :  Int = -1
    private  var lastSelectedItem :  Int = -1


    inner class ViewHolder(val binding : ItemSizeLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSizeLayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.btnSizeItem.text = list[position]


        holder.binding.btnSizeItem.setOnClickListener {

            onClickSize.onClickSize(holder.binding.btnSizeItem as Button,position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface SizeOnClickInterface{
    fun  onClickSize(button: Button, position:Int)
}