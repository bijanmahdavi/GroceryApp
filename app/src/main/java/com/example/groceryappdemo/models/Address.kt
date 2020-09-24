package com.example.groceryappdemo.models

data class Address (
    var pinCode: Int,
    var city: String,
    var streetName: String,
    var houseNo: Int,
    var houseType: String,
    var userID: String
)