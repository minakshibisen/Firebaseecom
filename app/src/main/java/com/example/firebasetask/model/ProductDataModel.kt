package com.example.firebasetask.model

data class ProductDataModel(
    val `data`: List<Data>,
    val msg: String,
    val result: Boolean
) {
    data class Data(
        val title: String,
        val description: String,
        val productList: List<Product>
    ) {
        data class Product(
            val _id: String,
            val image: String,
            val name: String,
            val price: String,
            val quantity: String
        )
    }
}