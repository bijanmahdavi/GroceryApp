package com.example.groceryappdemo.models

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
){
    companion object{
        const val KEY_CAT_ID = "catId"
    }
}