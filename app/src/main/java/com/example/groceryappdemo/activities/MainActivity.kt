package com.example.groceryappdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.CategoryAdapter
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.CategoryDataItem
import com.example.groceryappdemo.models.DetailsData
import com.example.groceryappdemo.models.User
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.recycler_view_home
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var sessionManager: SessionManager
    lateinit var guestUser: User
    lateinit var progressBar: ProgressBar
    lateinit var dbHelper: DBHelper

    var textViewCartCount: TextView? = null

    private var mList: ArrayList<CategoryDataItem> = ArrayList()
    private var adapterImg: CategoryAdapter? = null

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
           sessionManager = SessionManager(this)
           //if(sessionManager.getUserOnline()) {
               //startActivity(Intent(this, MainActivity::class.java))
               //finish()
           //}
        init()
    }

    private fun init() {
        setupToolBar()
        dbHelper = DBHelper(this)
        progressBar = progress_bar_home
        drawerLayout = drawer_layout
        navView = nav_view

        guestUser = User("Guest", "Login for more features")


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

        //loads data into RV and sets up manager
        getData()
        adapterImg = CategoryAdapter(this, mList)
        recycler_view_home.layoutManager = GridLayoutManager(this, 2)
        recycler_view_home.adapter = adapterImg
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
                progressBar.visibility = View.GONE
            },
            {

            }
        )
        requestQueue.add(request)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}