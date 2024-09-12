package com.example.firebasetask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetask.R
import com.example.firebasetask.adapter.ProductCartAdapter
import com.example.firebasetask.databinding.ActivityProductCartBinding
import com.example.firebasetask.model.ProductCartModel
import com.example.firebasetask.model.ProductDataModel

class ProductCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductCartBinding
    private val data = ArrayList<ProductCartModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data.add(
            ProductCartModel(
                "Puma",
                "Sold by: padmawati enterprises",
                2000,
                "Black",
                "UK9",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
            )
        )
        data.add(
            ProductCartModel(
                "NiKe",
                "Sold by: padmawati enterprises",
                8000,
                "White",
                "UK8",
                "https://assets.ajio.com/medias/sys_master/root/20231005/iCEC/651ed69fddf779151921eada/-473Wx593H-460479910-blue-MODEL.jpg",
            )
        )
        data.add(
            ProductCartModel(
                "Paragon",
                "Sold by: padmawati enterprises",
                1000,
                "Pink",
                "UK9",
                "https://assets.ajio.com/medias/sys_master/root/20231005/iCEC/651ed69fddf779151921eada/-473Wx593H-460479910-blue-MODEL.jpg",
            )
        )
        data.add(
            ProductCartModel(
                "Puma",
                "Sold by: padmawati enterprises",
                5000,
                "Red",
                "UK9",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
            )
        )

        binding.recyclerCart.adapter = ProductCartAdapter(data, this)
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)

    }
}