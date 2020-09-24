package com.example.groceryappdemo.models

data class ProductDetails (
        val count: Int,
        val data: ArrayList<ProductData>,
        val error: Boolean
    )

    data class ProductData(
        val __v: Int,
        val _id: String,
        val catId: Int,
        val created: String,
        val description: String,
        val image: String,
        val mrp: Int,
        val position: Int,
        val price: Int,
        val productName: String,
        val quantity: Int,
        val status: Boolean,
        val subId: Int,
        val unit: String
    )
