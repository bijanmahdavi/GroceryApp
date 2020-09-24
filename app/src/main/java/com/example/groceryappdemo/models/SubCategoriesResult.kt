package com.example.groceryappdemo.models

data class SubCategoriesResult(
    val count: Int,
    val data: ArrayList<SubCategoryDataItem>,
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
){
    companion object {
        const val KEY_SUB_ID = "subId"
    }
}