package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
    }

    private fun init() {
        var productName = intent.getStringExtra("NAME")
        var description = intent.getStringExtra("DESC")
        var image = intent.getStringExtra("IMAGE")
        product_details_product_name.text = productName
        product_details_product_description.text = description
        Picasso.get()
            .load(Config.IMAGE_URL + image)
            .resize(500,500)
            .centerCrop()
            .placeholder(R.drawable.comfort_food_placeholder)
            .error(R.drawable.comfort_food_placeholder)
            .into(product_details_image_view)
    }
}