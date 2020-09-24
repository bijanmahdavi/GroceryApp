package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.AddressAdapter
import com.example.groceryappdemo.models.Address
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class AddressActivity : AppCompatActivity() {
    val mList = ArrayList<Address>()
    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Add Address"
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
        setContentView(R.layout.activity_address)
        getData()
        init()
    }

    private fun getData() {
        //val request = StringRequest(


    }

    private fun init() {
        setupToolBar()
        setupAdapter()
        add_new_address_button.setOnClickListener {
            startActivity(Intent(applicationContext, AddAddressActivity::class.java))
        }
    }

    private fun setupAdapter() {
        val adapter = AddressAdapter(this, mList)
    }
}