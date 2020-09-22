package com.example.groceryappdemo.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.Adapters.TabAdapter
import com.example.groceryappdemo.Models.CategoryDataItem
import com.example.groceryappdemo.Models.SubCategoriesResult
import com.example.groceryappdemo.Models.SubCategoryDataItem
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    var catId = 0
    val mList: ArrayList<SubCategoryDataItem> = ArrayList()
    lateinit var myAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        catId = intent.getIntExtra(CategoryDataItem.KEY_CAT_ID, 1)
        init()
    }

    private fun init() {
        getData(catId)
        myAdapter = TabAdapter(supportFragmentManager)
    }

    private fun getData(catId: Int) {
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catId),
            {
                var gson = Gson()
                var subCatResponse = gson.fromJson(it, SubCategoriesResult::class.java)
                mList.addAll(subCatResponse.data)
                for(i in 0 until mList.size) {
                    myAdapter.addFragment(mList[i])
                }
                view_pager.adapter = myAdapter
                tab_layout.setupWithViewPager(view_pager) // tab nav bar on top
            },
            {

            }
        )
        Volley.newRequestQueue(this).add(request)
    }
}