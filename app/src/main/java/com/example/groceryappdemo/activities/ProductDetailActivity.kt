package com.example.groceryappdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Config
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.database.DBHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class ProductDetailActivity : AppCompatActivity() {
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
            R.id.action_logout -> logout()
        }
        return true
    }
    private fun logout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Really want to logout?")
        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                Toast.makeText(applicationContext, "See you next time!", Toast.LENGTH_SHORT).show()
                SessionManager(applicationContext).logout()
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finishAffinity()
            }
        })
        builder.setNegativeButton("No", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        var alertDialog = builder.create()
        alertDialog.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
    }

    private fun init() {
        setupToolBar()
        dbHelper = DBHelper(this)
        var id = intent.getStringExtra("_ID")
        var productName = intent.getStringExtra("NAME")
        toolbar.title = productName
        var price = intent.getIntExtra("PRICE", 0)
        var description = intent.getStringExtra("DESC")
        var amount = intent.getIntExtra("AMOUNT", 1)
        var mrp = intent.getIntExtra("MRP", 0)
        var image = intent.getStringExtra("IMAGE")
        product_details_product_name.text = productName
        product_details_product_description.text = description
        Picasso.get()
            .load(Config.IMAGE_URL + image)
            .resize(500,500)
            .centerCrop()
            .placeholder(R.drawable.comfort_food_placeholder)
            .error(R.drawable.comfort_food_placeholder)
            .into(product_details_image_view)

        add_item_to_cart.setOnClickListener {
            dbHelper.addItem(id,productName,price, mrp, amount, image)
            startActivity(Intent(this, MainActivity::class.java))
            Log.d("dbg", dbHelper.getItems().toString())
        }
    }


}