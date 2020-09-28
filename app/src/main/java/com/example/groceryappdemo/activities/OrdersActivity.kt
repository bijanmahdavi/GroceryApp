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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.AddressAdapter
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Address
import com.example.groceryappdemo.models.Item
import com.example.groceryappdemo.models.OrderSummary
import com.example.groceryappdemo.models.User
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.json.JSONObject

class OrdersActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private val mList = ArrayList<OrderSummary>()
    private var itemList = ArrayList<Item>()
    private lateinit var sessionManager: SessionManager
    lateinit var dbHelper: DBHelper
    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        sessionManager = SessionManager(this)
        dbHelper = DBHelper(this)
        getOrdersData()
        init()
    }

    private fun getOrdersData() {
        /*val request =
            StringRequest(
                Request.Method.GET,
                Endpoints.getAddressByID(sessionManager.getOnlineUserID()),
                {
                    val jsonObj = JSONObject(it)
                    val data = jsonObj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        Log.d("DA_Data", data[i].toString())
                        val address = data.getJSONObject(i)
                        val pinCode = address.getInt("pincode")
                        val streetName = address.getString("streetName")
                        val city = address.getString("city")
                        val houseNo = address.getString("houseNo")
                        val type = address.getString("type")
                        mList.add(Address(pinCode, city, streetName, houseNo.toInt(), type, sessionManager.getOnlineUserID()))
                    }
                    val adapter = AddressAdapter(this, mList)
                    address_RV.adapter = adapter
                    address_RV.layoutManager = LinearLayoutManager(this)
                }, {
                    Toast.makeText(this, "ERROR!!", Toast.LENGTH_SHORT).show()
                })
        Volley.newRequestQueue(this).add(request)*/
    }

/*    private fun getOrderData() : ArrayList<OrderSummary> {
        //return dbHelper.getOrderSummarys()
    }*/

    private fun init() {
        //itemList = getItemData()
        drawerLayout = drawer_layout_orders
        navView = nav_view_orders
        setupToolBar()
        var headerView = navView.getHeaderView(0)
        var guestUser = User("Guest", "Login for more features")
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
}