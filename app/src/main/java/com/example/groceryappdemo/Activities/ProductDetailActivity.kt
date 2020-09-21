package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryappdemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
    }

    private fun init() {
        val url = "http://rjtmobile.com/grocery/images/"
        var productName = intent.getStringExtra("NAME")
        var description = intent.getStringExtra("DESC")
        var image = intent.getStringExtra("IMAGE")
        product_details_product_name.text = productName
        product_details_product_description.text = description
        Picasso.get()
            .load(url + image)
            .resize(500,500)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(product_details_image_view)
    }
}