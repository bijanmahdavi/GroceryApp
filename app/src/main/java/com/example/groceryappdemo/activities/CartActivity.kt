package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.DBViewAdapter
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class CartActivity : AppCompatActivity(), View.OnClickListener {

    var mList: ArrayList<Item> = ArrayList()
    lateinit var dbHelper: DBHelper

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Item"
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
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = DBHelper(this)
        init()

    }

    private fun init() {
        setupToolBar()
        readData()
        button_read.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.button_read -> toAddressActivity()
        }
    }

    private fun toAddressActivity() {
        startActivity(Intent(this, AddressActivity::class.java))
    }

    private fun readData() {
        mList = dbHelper.getItems()
        val adapter = DBViewAdapter(this, mList)
        this.home_RV.adapter = adapter
        this.home_RV.layoutManager = LinearLayoutManager(this)
        adapter.setData(mList)
    }

/*    override fun onResume() {
        super.onResume()
        mList = dbHelper.getEmployees()
    }*/
}