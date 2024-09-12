package com.example.firebasetask.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.firebasetask.R
import com.example.firebasetask.adapter.SizeAdapter
import com.example.firebasetask.adapter.SizeOnClickInterface
import com.example.firebasetask.databinding.ActivityProductDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class ProductDetailActivity : AppCompatActivity(), SizeOnClickInterface {
    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var productDatabaseReference: DatabaseReference
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUID :  String
    private lateinit var orderImageUrl:String
    private lateinit var orderName:String
    private var orderSize:String?  = null
    private var orderQuantity:Int  = 1
    private lateinit var orderPrice:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // region implements firebase product display

        val sizeList = ArrayList<String>()
        sizeList.add("5")
        sizeList.add("6")
        sizeList.add("7")
        sizeList.add("8")
        sizeList.add("9")
        sizeList.add("10")


        sizeAdapter = SizeAdapter( sizeList , this)
        binding.sizeRecycler.adapter = SizeAdapter(sizeList, this)
        binding.sizeRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel("https://assets.ajio.com/medias/sys_master/root/20231229/q6p8/658eb386afa4cf41f5f59c12/-473Wx593H-469534253-black-MODEL5.jpg"))
        imageList.add(SlideModel("https://assets.ajio.com/medias/sys_master/root/20231229/vetQ/658ebdc8ddf7791519f573b6/-473Wx593H-469534253-black-MODEL3.jpg"))
        imageList.add(SlideModel("https://assets.ajio.com/medias/sys_master/root/20231229/okhr/658eb8a0ddf7791519f554c2/-1117Wx1400H-469534253-black-MODEL4.jpg"))

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        binding.textAddCart.setOnClickListener {

          startActivity(Intent(this,ProductCartActivity::class.java))
        }

    }

    override fun onClickSize(button: Button, position: Int) {
        orderSize = button.text.toString()
        Toast.makeText(this, "Size ${button.text} Selected", Toast.LENGTH_SHORT).show()

    }
}