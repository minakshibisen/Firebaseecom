package com.example.firebasetask.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasetask.activity.SearchResultActivity
import com.example.firebasetask.adapter.AddAdapter
import com.example.firebasetask.databinding.FragmentAddBinding
import com.example.firebasetask.model.ProductDataModel
import com.example.firebasetask.model.ProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var collectionListArr = arrayListOf<ProductModel>()
    private val data = ArrayList<ProductDataModel.Data>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

               binding.rellSearch.setOnClickListener {
                   val keyword = binding.textSearch.text.toString()
                   if (keyword.isNotEmpty()) {

                       val intent = Intent(context, SearchResultActivity::class.java).apply {
                           putExtra("SEARCH_KEYWORD", keyword)
                       }
                       startActivity(intent)
                   }
               }
        val product1 = ArrayList<ProductDataModel.Data.Product>()
        val product2 = ArrayList<ProductDataModel.Data.Product>()
        val product3 = ArrayList<ProductDataModel.Data.Product>()
        product1.add(
            ProductDataModel.Data.Product(
                "fgf",
                "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSdM5cQkzpX1uPHecwSg_W23rDbLp4-WFKPzkJWGYUWTXdnjLLo3kqsM3g6uo0j2DuEXWn6yEl87_kLBq7-icvmILAnWjFK3fm8-Ug4B9JyDm6WKD4MDJWQ",
                "PUMA",
                "Rs.20,000",
                "$2"
            )
        )
        product1.add(
            ProductDataModel.Data.Product(
                "vhvbjg",
                "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSdM5cQkzpX1uPHecwSg_W23rDbLp4-WFKPzkJWGYUWTXdnjLLo3kqsM3g6uo0j2DuEXWn6yEl87_kLBq7-icvmILAnWjFK3fm8-Ug4B9JyDm6WKD4MDJWQ",
                "v7utgk f",
                "Rs.20,000",
                "$2"
            )
        )
        product1.add(
            ProductDataModel.Data.Product(
                "hv",
                "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSdM5cQkzpX1uPHecwSg_W23rDbLp4-WFKPzkJWGYUWTXdnjLLo3kqsM3g6uo0j2DuEXWn6yEl87_kLBq7-icvmILAnWjFK3fm8-Ug4B9JyDm6WKD4MDJWQ",
                "ckfmfch",
                "Rs.20,000",
                "$2"
            )
        )
        product2.add(
            ProductDataModel.Data.Product(
                "fgf",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
                "NIKE",
                "Rs.80,000",
                "$2"
            )
        )
        product2.add(
            ProductDataModel.Data.Product(
                "fgf",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
                "NIKE",
                "Rs.80,000",
                "$2"
            )
        )
        product2.add(
            ProductDataModel.Data.Product(
                "yt",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
                "dfhfdgh",
                "Rs.80,000",
                "$2"
            )
        )
        product2.add(
            ProductDataModel.Data.Product(
                "hrtyr",
                "https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/99486859-0ff3-46b4-949b-2d16af2ad421/custom-nike-dunk-high-by-you-shoes.png",
                "ghdfghd",
                "Rs.80,000",
                "$2"
            )
        )
        product3.add(
            ProductDataModel.Data.Product(
                "fgf",
                "https://assets.ajio.com/medias/sys_master/root/20231005/iCEC/651ed69fddf779151921eada/-473Wx593H-460479910-blue-MODEL.jpg",
                "CAMPUS",
                "Rs.20,724",
                "$2"
            )
        )

        data.add(ProductDataModel.Data("Exclusive Product", "View All", product1))
        data.add(ProductDataModel.Data("Exclusive Product", "View All", product2))
        data.add(ProductDataModel.Data("Exclusive Product", "View All", product3))

        binding.imageAdd.setOnClickListener {
            addItem()
        }

        binding.imageRemove.setOnClickListener {
            removeItem()
        }

        initializeCollectionList()

        return binding.root

    }
    private fun initializeCollectionList() {
        val model = ProductModel(1, "")
        collectionListArr.add(model)

        binding.recyclerview.adapter = AddAdapter(data, context)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeItem() {
        collectionListArr.removeAt(collectionListArr.size - 1)
        binding.recyclerview.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addItem() {
        val model = ProductModel(collectionListArr.size + 1, "")
        collectionListArr.add(model)
        binding.recyclerview.adapter?.notifyDataSetChanged()
    }
}