package com.example.groceryappdemo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.R
import com.example.groceryappdemo.activities.CartActivity
import com.example.groceryappdemo.activities.PaymentActivity
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.models.Address
import kotlinx.android.synthetic.main.address_list.view.*

class AddressAdapter (var mContext: Context, var mList: ArrayList<Address>) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    lateinit var sessionManager: SessionManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.MyViewHolder {

        var view = LayoutInflater.from(mContext).inflate(R.layout.address_list, parent, false)
        sessionManager = SessionManager(mContext)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressAdapter.MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: Address) {
            itemView.setOnClickListener {
                sessionManager.storeDefaultAddress(data.streetName, data.houseNo, data.city, data.pinCode)
                startActivity(mContext, Intent(mContext, CartActivity::class.java), bundleOf())
            }
            itemView.address_title.text = "Delivery Address"
            itemView.user_name.text = sessionManager.getUserFirstName()
            itemView.text_view_address.text = data.streetName+" House no. "+data.houseNo
            itemView.text_view_city.text = data.city+" - "+data.pinCode.toString()

        }
    }
}