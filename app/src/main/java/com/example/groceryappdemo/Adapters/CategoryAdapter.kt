package com.example.groceryappdemo.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryappdemo.Activities.SubCategoryActivity
import com.example.groceryappdemo.Models.CategoryDataItem
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_grid_layout.view.*

class CategoryAdapter(var mContext: Context, var mList: ArrayList<CategoryDataItem>) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.category_grid_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var images = mList[position]
        holder.bind(images)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<CategoryDataItem>) {
        mList = lst
        notifyDataSetChanged()
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(data: CategoryDataItem){
            Picasso.get()
                .load(Config.IMAGE_URL+data.catImage)
                .resize(0, 515)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.grid_img_src)
            val str = data.catName
            itemView.grid_text_view_title.text = str

            itemView.setOnClickListener{
                mContext.startActivity(Intent(mContext, SubCategoryActivity::class.java))
            }


        }

    }


}