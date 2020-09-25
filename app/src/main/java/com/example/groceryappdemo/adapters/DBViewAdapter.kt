package com.example.groceryappdemo.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.R
import com.example.groceryappdemo.activities.CartActivity
import com.example.groceryappdemo.activities.UpdateActivity
import com.example.groceryappdemo.app.Config
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list.view.*

class DBViewAdapter(var mContext: Context, var mList: ArrayList<Item>) : RecyclerView.Adapter<DBViewAdapter.MyViewHolder>() {

    lateinit var dbHelper: DBHelper
    var subTotal = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        dbHelper = DBHelper(mContext)
        var view = LayoutInflater.from(mContext).inflate(R.layout.cart_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<Item>) {
        mList = lst
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(data: Item) {
            Log.d("data", data.toString())
            //subTotal = data.amount
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
            //itemView.sub_total.text = "Subtotal: "+data.amount*data.price

            itemView.delete_employee_button.setOnClickListener {
                dbHelper.deleteItem(data.id)
                mContext.startActivity(Intent(mContext, CartActivity::class.java))
            }

            itemView.edit_employee_button.setOnClickListener {

                itemView.edit_employee_button.visibility = View.GONE
                itemView.item_quantity.visibility = View.GONE
                itemView.add_quantity_button.visibility = View.VISIBLE
                itemView.item_quantity_after_edit.visibility = View.VISIBLE
                itemView.item_quantity_after_edit.text = data.amount.toString()
                itemView.subtract_quantity_button.visibility = View.VISIBLE

            }

            itemView.subtract_quantity_button.setOnClickListener {
                if(data.amount >= 1) {
                    data.amount--
                    itemView.item_quantity.text = "Quantity: " + data.amount
                    itemView.item_quantity_after_edit.text = data.amount.toString()
                    dbHelper.updateItem(data.id, data.name, data.price, data.amount)
                } else {
                    dbHelper.deleteItem(data.id)
                    mContext.startActivity(Intent(mContext, CartActivity::class.java))
                }
            }
            itemView.add_quantity_button.setOnClickListener {
                data.amount++
                itemView.item_quantity.text = "Quantity: "+data.amount
                itemView.item_quantity_after_edit.text = data.amount.toString()
                dbHelper.updateItem(data.id, data.name, data.price, data.amount)
            }

        }
    }
}