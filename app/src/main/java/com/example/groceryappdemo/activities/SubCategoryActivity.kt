package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.adapters.TabAdapter
import com.example.groceryappdemo.models.CategoryDataItem
import com.example.groceryappdemo.models.SubCategoriesResult
import com.example.groceryappdemo.models.SubCategoryDataItem
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class SubCategoryActivity : AppCompatActivity() {

    var catId = 0
    val mList: ArrayList<SubCategoryDataItem> = ArrayList()
    lateinit var myAdapter: TabAdapter

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Categories"
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
            R.id.action_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.action_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.action_profile -> Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
            R.id.action_logout -> logout()
        }
        return true
    }
    private fun logout() {
        SessionManager(this).logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        catId = intent.getIntExtra(CategoryDataItem.KEY_CAT_ID, 1)
        init()
    }

    private fun init() {
        setupToolBar()
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