package com.example.groceryappdemo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.activities.ProductDetailActivity
import com.example.groceryappdemo.models.ProductData
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_products.view.*


class SubProductAdapter(private val context: Context, private val list: ArrayList<ProductData>) : RecyclerView.Adapter<ProductDetailResponse>() {
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
            intent.putExtra("__V", item.__v)
            intent.putExtra("_ID", item._id)
            intent.putExtra("CATID", item.catId)
            intent.putExtra("CREATED", item.created)
            intent.putExtra("DESCRIPTION", item.description)
            intent.putExtra("IMAGE", item.image)
            intent.putExtra("MRP", item.mrp)
            intent.putExtra("POSITION", item.position)
            intent.putExtra("PRICE", item.price)
            intent.putExtra("NAME", item.productName)
            intent.putExtra("QUANTITY", item.quantity)
            intent.putExtra("STATUS", item.status)
            intent.putExtra("SUBID", item.subId)
            intent.putExtra("UNIT", item.unit)
            view.context.startActivity(intent)
        }
    }
}
