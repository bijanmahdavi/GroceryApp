package com.example.groceryappdemo.models

data class OrderSummary (
    val _id: String ?= null,
    var orderAmount: Int ?= null,
    var totalAmount: Int,
    var ourPrice: Int,
    var discount: Int,
    var deliveryCharges: Int
)