package com.example.groceryappdemo.Models

data class DetailsData (
    var error: Boolean? = null,
    var count: Int?,
    var data: List<CategoryDataItem>
)

data class CategoryDataItem(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
)

data class SubCategoriesResult(
    val count: Int,
    val `data`: List<SubCategoryDataItem>,
    val error: Boolean
)

data class SubCategoryDataItem(
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)

data class ProductResponse(
    val count: Int,
    val `data`: List<ProductData>,
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