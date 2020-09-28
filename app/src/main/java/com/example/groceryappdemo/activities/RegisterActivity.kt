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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import kotlinx.android.synthetic.main.content_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sessionManager = SessionManager(this)
        if(sessionManager.getUserOnline()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        init()
    }

    private fun init() {
        setupToolBar()
        drawerLayout = drawer_layout_register
        navView = nav_view_register
        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        submit_button_to_login.setOnClickListener {
            var name = username_login_edit_text.text.toString()
            var password = password_login_edit_text.text.toString()
            var confirmPassword = confirm_password_login_edit_text.text.toString()
            var email = email_register_edit_text.text.toString()

            var params = HashMap<String , String>()
            params["firstName"] = name
            params["password"] = password
            params["mobile"] = "12345"
            params["email"] = email

            var jsonObject = JSONObject(params as Map<*, *>)

            if(name.isNotEmpty() && email.isNotEmpty() && password.length > 5 && password == confirmPassword ) {
                var request = JsonObjectRequest(
                    Request.Method.POST,
                    Endpoints.getRegister(),
                    jsonObject,
                    {
                        Log.d("dbg", it.toString())
                        Toast.makeText(applicationContext, "registered", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("pass", password)
                        startActivity(intent)

                    },
                    {
                        Log.d("register error", it.toString())
                    }
                )

                Volley.newRequestQueue(this).add(request)
            } else {
                Toast.makeText(applicationContext, "Error Registering", Toast.LENGTH_SHORT).show()
            }
        }

        to_login_screen_text_button.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

        }

    }
    private fun setupToolBar() {
        var toolbar = toolbar
        if(sessionManager.getUserOnline()) {
            toolbar.title = "Welcome user:" + SessionManager(this).getOnlineUserID()
        } else {
            toolbar.title = "Welcome Guest!"
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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