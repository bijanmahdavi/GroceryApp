package com.example.groceryappdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.PaymentAdapter
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import com.example.groceryappdemo.models.User
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.content_payment.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var sessionManager: SessionManager
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null

    var mList: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        sessionManager = SessionManager(this)
        init()
    }

    private fun getItemData() : ArrayList<Item> {
        return dbHelper.getItems()
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
                //startActivity(Intent(applicationContext, LoginActivity::class.java))
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


    private fun init() {
        setupToolBar()
        dbHelper = DBHelper(this)
        drawerLayout = drawer_layout_summary
        navView = nav_view_summary

        val guestUser = User("Guest", "Login for more features")
        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        mList = getItemData()
        val adapter = PaymentAdapter(this, mList)
        this.confirm_order_RV.adapter = adapter
        this.confirm_order_RV.layoutManager = LinearLayoutManager(this)
        adapter.setData(mList)

        button_credit_card.setOnClickListener {
            //startActivity()
        }
        button_cash.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Order Confirmation")
            builder.setMessage("Are you sure you want to place your order?")

            builder.setPositiveButton("Yes") {
                dialog, p1 ->
                Toast.makeText(applicationContext, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                sendOrder()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            builder.setNegativeButton("No") {
                dialog, p1 ->
                dialog?.dismiss()
            }

            var alertDialog = builder.create()
            alertDialog.show()
        }
        var orderSummary = dbHelper.getOrderSummary()
        cart_item_total.text = "Total Items: "+dbHelper.getCartTotalQuantity().toString()
        original_price_summary.text = "Total: $"+dbHelper.getCartSubtotal().toString()
        discount_summary.text = "Discount: $0"
        delivery_fee_summary.text = "Delivery: $"+orderSummary.deliveryCharges.toString()
        var subtotal = dbHelper.getCartSubtotal() + orderSummary.deliveryCharges
        sub_total_summary.text = "Subtotal: $"+subtotal

    }
    private fun sendOrder() {
        var params = HashMap<String , String>()
        var gson = Gson()
        var obj = gson.toJson(mList)
        Log.d("mList", obj.toString())
        //params[""] = mList.
        //params[""] =
        //params[""] =
        //params[""] =

        var jsonObject = JSONObject(params as Map<*, *>)
        Log.d("jsonOBJ", jsonObject.toString())
        dbHelper.emptyCart()
        /*var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getPostOrders(),
            jsonObject,
            {
                Log.d("dbg", it.toString())
                Toast.makeText(applicationContext, "order posted", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            },
            {
                Log.d("error", it.toString())
            }
        )

        Volley.newRequestQueue(this).add(request)*/
    }
}