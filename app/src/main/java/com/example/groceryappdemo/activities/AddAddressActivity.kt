package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_add_address)
        //var spinner = findViewById<Spinner>(R.id.address_type)
        init()
    }

    private fun init() {
        setupToolBar()
        save_address_button.setOnClickListener {
            var pinCode = address_pin_name.text.toString()
            var city = address_city_name.text
            var streetName = address_name.text
            var houseNo = address_house_number.text.toString()
            var houseType = address_type.text
            var userID = "id"
            var params = HashMap<String, Any>()
            params["pinCode"] = pinCode.toInt()
            params["city"] = city
            params["streetName"] = streetName
            params["houseNo"] = houseNo.toInt()
            params["houseType"] = houseType
            params["userId"] = 1

            var jsonObject = JSONObject(params as Map<*,*>)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getAddress(),
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