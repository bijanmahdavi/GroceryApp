package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.Adapters.CategoryAdapter
import com.example.groceryappdemo.Models.CategoryDataItem
import com.example.groceryappdemo.Models.DetailsData
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class HomeActivity : AppCompatActivity() {
    private var mList: ArrayList<CategoryDataItem> = ArrayList()
    private var adapterImg: CategoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Welcome"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.action_cart -> Toast.makeText(applicationContext, "Shopping Cart", Toast.LENGTH_SHORT).show()
            R.id.action_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.action_profile -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
        }
        return true
    }


    private fun init() {
        setupToolBar()
        getData()
        adapterImg = CategoryAdapter(this, mList)
        recycler_view_home.layoutManager = GridLayoutManager(this, 2)
        recycler_view_home.adapter = adapterImg
    }

    private fun getData(){
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
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