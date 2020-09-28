package com.example.groceryappdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.DBViewAdapter
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import com.example.groceryappdemo.models.User
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.content_cart.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import kotlinx.android.synthetic.main.nav_header.view.*

class CartActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    var textViewCartCount: TextView? = null
    var mList: ArrayList<Item> = ArrayList()
    private lateinit var dbHelper: DBHelper
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        dbHelper = DBHelper(this)
        init()

    }

    private fun init() {
        sessionManager = SessionManager(this)
        setupToolBar()
        drawerLayout = drawer_layout_cart
        navView = nav_view_cart

        var guestUser = User("Guest", "Login for more features")
        var headerView = navView.getHeaderView(0)

        if(sessionManager.getUserOnline()) {
            var name = sessionManager.getUserFirstName()
            var email = sessionManager.getUserEmail()
            headerView.text_view_header_name.text = name
            headerView.text_view_header_email.text = email
        } else {
            headerView.text_view_header_name.text = guestUser.name
            headerView.text_view_header_email.text = guestUser.email
        }

        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        readData()
        button_checkout.setOnClickListener(this)
        default_address_container.setOnClickListener(this)
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        if(sessionManager.getUserOnline()) {
            toolbar.title = "Welcome, " + SessionManager(this).getUserFirstName()
        } else {
            toolbar.title = "Welcome, Guest!"
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //cart icon with dynamic cart size bubble
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        var item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        view.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        updateCartCount()

        return true
    }

    private fun updateCartCount() {
        val count = dbHelper.getCartTotalQuantity()
        if(count == 0) {
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home_button -> startActivity(Intent(this, MainActivity::class.java))
            R.id.action_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.item_orders -> startActivity(Intent(this, OrdersActivity::class.java))
            R.id.item_logout -> logout()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
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

    override fun onClick(view: View) {
        when(view.id){
            R.id.button_checkout -> toCheckoutActivity()
            R.id.default_address_container -> toAddressActivity()
        }
    }
    private fun toCheckoutActivity() {

        if(sessionManager.getUserOnline()) {
            startActivity(Intent(this, PaymentActivity::class.java))
        } else {
            Toast.makeText(applicationContext, "Please Register first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun toAddressActivity() {

        if(sessionManager.getUserOnline()) {
            startActivity(Intent(this, AddressActivity::class.java))
        } else {
            Toast.makeText(applicationContext, "Please Register first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun readData() {
        mList = dbHelper.getItems()
        var orderSummary = dbHelper.getOrderSummary()

        val adapter = DBViewAdapter(this, mList)
        if(mList.size == 0) {
            original_price.visibility = View.INVISIBLE
            discount.visibility = View.INVISIBLE
            delivery_fee.visibility = View.INVISIBLE
            sub_total.visibility = View.INVISIBLE
        } else {
            original_price.visibility = View.VISIBLE
            discount.visibility = View.VISIBLE
            delivery_fee.visibility = View.VISIBLE
            sub_total.visibility = View.VISIBLE
            original_price.text = "Total: $" + dbHelper.getCartSubtotal().toString()
            discount.text = "Discount: $0"//+orderSummary.discount.toString()
            delivery_fee.text = "Delivery: $" + orderSummary.deliveryCharges.toString()
            var subtotal = dbHelper.getCartSubtotal() + orderSummary.deliveryCharges
            sub_total.text = "Subtotal: $" + subtotal
        }
        default_address.text = sessionManager.getDefaultStreet()
        default_address_zip.text = sessionManager.getDefaultZip().toString()
        this.home_RV.adapter = adapter
        this.home_RV.layoutManager = LinearLayoutManager(this)
        adapter.setData(mList)
        adapter.notifyDataSetChanged()
    }

/*    override fun onResume() {
        super.onResume()
        mList = dbHelper.getEmployees()
    }*/
}