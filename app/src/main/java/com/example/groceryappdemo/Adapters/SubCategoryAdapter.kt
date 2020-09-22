package com.example.groceryappdemo.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.Activities.ProductDetailActivity
import com.example.groceryappdemo.Models.SubCategoryDataItem
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_view_adapter.view.*

class SubCategoryAdapter(var mContext: Context, var mList: ArrayList<SubCategoryDataItem>) : RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.list_view_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var images = mList[position]
        holder.bind(images)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<SubCategoryDataItem>) {
        mList = lst
        notifyDataSetChanged()
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(data: SubCategoryDataItem){
            Picasso.get()
                .load(Config.IMAGE_URL+data.subImage)
                .resize(0, 515)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.list_image)
            Log.d("Data", data.toString())

            itemView.desc_text_view_title.text = data.subName
            itemView.text_view_description.text = data.subDescription

            itemView.setOnClickListener{
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra("ID", data.catId)
                intent.putExtra("DESC", data.subDescription)
                intent.putExtra("IMAGE", data.subImage)
                intent.putExtra("NAME", data.subName)
                mContext.startActivity(intent)
            }


        }

    }


}