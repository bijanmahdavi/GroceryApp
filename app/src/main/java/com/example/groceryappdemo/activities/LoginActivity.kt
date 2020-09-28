package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.content_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this)
        if(sessionManager.getUserOnline()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        if(sessionManager.getUserOnline()) {
            toolbar.title = "Welcome, user:" + SessionManager(this).getOnlineUserID()
        } else {
            toolbar.title = "Welcome, Guest!"
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {

        setupToolBar()
        drawerLayout = drawer_layout_login
        navView = nav_view_login

        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        submit_button_to_home.setOnClickListener{
            var loginEmail = email_edit_text.text.toString()
            var loginPassword = password_edit_text.text.toString()

            var params = HashMap<String, String>()
            params["email"] = loginEmail
            params["password"] = loginPassword

            var jsonObject = JSONObject(params as Map<*,*>)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                jsonObject,
                {
                    var id = it.getJSONObject("user").getString("_id")
                    var email = it.getJSONObject("user").getString("email")
                    var firstName = it.getJSONObject("user").getString("firstName")
                    sessionManager.storeID(id)
                    sessionManager.storeEmail(email)
                    sessionManager.storeFirstName(firstName)
                    Log.d("first_name", firstName.toString())
                    Toast.makeText(this,"Login Success!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                },
                {
                    Log.d("dbg", it.toString())
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                })
            Volley.newRequestQueue(this).add(request)
        }

        to_register_screen_text_button.setOnClickListener{

            startActivity(Intent(this, RegisterActivity::class.java))

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.home_button -> startActivity(Intent(this, MainActivity::class.java))
            R.id.action_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.item_orders -> startActivity(Intent(this, OrdersActivity::class.java))
            R.id.item_logout -> Toast.makeText(this,"you are not logged in", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}