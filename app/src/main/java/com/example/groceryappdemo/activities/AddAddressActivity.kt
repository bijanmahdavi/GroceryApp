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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)
        //var spinner = findViewById<Spinner>(R.id.address_type)
        init()
    }
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

    private fun init() {
        setupToolBar()
        save_address_button.setOnClickListener {
            var pinCode = address_pin_name.text.toString()
            var city = address_city_name.text.toString()
            var streetName = address_name.text.toString()
            var houseNo = address_house_number.text.toString()
            var houseType = address_type.text
            var userID = sessionManager.getOnlineUserID()
            var params = HashMap<String, Any>()
            params["pincode"] = pinCode.toInt()
            params["city"] = city
            params["streetName"] = streetName
            params["houseNo"] = houseNo.toInt()
            params["type"] = "house"
            params["userId"] = userID
            Log.d("shite", params.entries.toString())
            Log.d("endp", Endpoints.saveAddress())
            var jsonObject = JSONObject(params as Map<*, *>)
            Log.d("Bruh", jsonObject.toString())

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.saveAddress(),
                jsonObject,
                {
                    Toast.makeText(this,"Address added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AddressActivity::class.java))
                },
                {
                    Toast.makeText(this,"Error adding address", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        }

    }
}