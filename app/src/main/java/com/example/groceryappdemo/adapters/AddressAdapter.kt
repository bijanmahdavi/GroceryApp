package com.example.groceryappdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.R
import com.example.groceryappdemo.models.Address
import kotlinx.android.synthetic.main.address_list.view.*

class AddressAdapter (var mContext: Context, var mList: ArrayList<Address>) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.MyViewHolder {

        var view = LayoutInflater.from(mContext).inflate(R.layout.address_list, parent, false)
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
            itemView.text_view_address.text = "Test"
            itemView.text_view_city.text = data.city
            itemView.text_view_pin.text = data.pinCode.toString()
        }
    }
}