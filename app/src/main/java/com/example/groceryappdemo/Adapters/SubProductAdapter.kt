package com.example.groceryappdemo.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.Activities.ProductDetailActivity
import com.example.groceryappdemo.Models.ProductData
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_products.view.*


class SubProductAdapter(private val context: Context, private val list: ArrayList<ProductData>) :
    RecyclerView.Adapter<ProductDetailResponse>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailResponse {
        val view = LayoutInflater.from(context).inflate(R.layout.list_products, parent, false)
        return ProductDetailResponse(view)
    }

    override fun onBindViewHolder(holder: ProductDetailResponse, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ProductDetailResponse(private val view: View) : RecyclerView.ViewHolder(view) {

    var intent = Intent(view.context, ProductDetailActivity::class.java)

    fun bind(item: ProductData) {
        val url = Config.IMAGE_URL+item.image
        view.subCata_head.text = item.productName

        Picasso.get().load(url).placeholder(R.drawable.comfort_food_placeholder).into(view.subCata_img)

        view.setOnClickListener {
            intent.putExtra("NAME", item.productName)
            intent.putExtra("DESC", item.description)
            intent.putExtra("IMAGE", item.image)
            view.context.startActivity(intent)
        }
    }
}
