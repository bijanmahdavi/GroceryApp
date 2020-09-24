package com.example.groceryappdemo.adapters

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_cart.view.*
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
            itemView.employee_name.text = "Item Name: "+data.name
            //itemView.employee_id.text = "ID: "+data.id.toString()
            itemView.employee_email.text = "Quantity: "+data.amount
            //itemView.sub_total.text = "Subtotal: "+data.amount*data.price

            itemView.delete_employee_button.setOnClickListener {
                dbHelper.deleteItem(data.id)
                mContext.startActivity(Intent(mContext, CartActivity::class.java))
            }

            itemView.edit_employee_button.setOnClickListener {
                var intent = Intent(mContext, UpdateActivity::class.java)
                intent.putExtra("ID", data.id)
                intent.putExtra("NAME", data.name)
                intent.putExtra("AMOUNT", data.amount)
                intent.putExtra("IMAGE", data.image)
                itemView.edit_employee_button.visibility = View.GONE
                itemView.add_quantity_button.visibility = View.VISIBLE
                itemView.subtract_quantity_button.visibility = View.VISIBLE

            }

            itemView.subtract_quantity_button.setOnClickListener {
                data.amount--
                if(data.amount >= 1) {
                    itemView.employee_email.text = "Quantity: " + data.amount
                    dbHelper.updateItem(data.id, data.name, data.price, data.amount)
                } else {
                    dbHelper.deleteItem(data.id)
                    mContext.startActivity(Intent(mContext, CartActivity::class.java))
                }
            }
            itemView.add_quantity_button.setOnClickListener {
                data.amount++
                itemView.employee_email.text = "Quantity: "+data.amount
                dbHelper.updateItem(data.id, data.name, data.price, data.amount)
            }

        }
    }
}