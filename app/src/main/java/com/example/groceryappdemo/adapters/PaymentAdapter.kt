package com.example.groceryappdemo.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list.*
import kotlinx.android.synthetic.main.cart_list.view.*

class PaymentAdapter(var mContext: Context, var mList: ArrayList<Item>) : RecyclerView.Adapter<PaymentAdapter.MyViewHolder>() {

    lateinit var dbHelper: DBHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.MyViewHolder {
        dbHelper = DBHelper(mContext)
        var view = LayoutInflater.from(mContext).inflate(R.layout.confirm_cart_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentAdapter.MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<Item>) {
        mList = lst
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Item) {
            Picasso.get()
                .load(Config.IMAGE_URL + data.image)
                .resize(500,500)
                .centerCrop()
                .placeholder(R.drawable.comfort_food_placeholder)
                .error(R.drawable.comfort_food_placeholder)
                .into(itemView.cart_image_view)
            itemView.item_name.text = data.name
            //itemView.employee_id.text = "ID: "+data.id.toString()
            itemView.item_quantity.text = "Quantity: "+data.amount
            itemView.item_price.text = "$"+data.price.toString()
            itemView.item_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.item_MRP.text = "$"+data.price.toString()

        }
    }

}