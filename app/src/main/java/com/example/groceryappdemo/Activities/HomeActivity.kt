package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.Adapters.CategoryAdapter
import com.example.groceryappdemo.Models.CategoryDataItem
import com.example.groceryappdemo.Models.DetailsData
import com.example.groceryappdemo.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var mList: ArrayList<CategoryDataItem> = ArrayList()
    private var adapterImg: CategoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        getData()
        adapterImg = CategoryAdapter(this, mList)
        recycler_view_home.layoutManager = GridLayoutManager(this, 2)
        recycler_view_home.adapter = adapterImg
    }

    private fun getData(){
        var url = "https://grocery-second-app.herokuapp.com/api/category"
        val link = "http://rjtmobile.com/grocery/images/"
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            url,
            {
                var gson = Gson()
                var dataResult = gson.fromJson(it, DetailsData::class.java)
                dataResult.data.toCollection(mList)
                adapterImg?.setData(mList)
            },
            {

            }
        )
        requestQueue.add(request)
    }
}