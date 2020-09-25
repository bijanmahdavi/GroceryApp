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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.AddressAdapter
import com.example.groceryappdemo.app.Endpoints
import com.example.groceryappdemo.app.SessionManager
import com.example.groceryappdemo.models.Address
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.address_list.*
import kotlinx.android.synthetic.main.address_list.address_container
import kotlinx.android.synthetic.main.address_list.view.*
import kotlinx.android.synthetic.main.app_tool_bar.*
import org.json.JSONObject

class AddressActivity : AppCompatActivity() {
    private val mList = ArrayList<Address>()
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        sessionManager = SessionManager(this)
        getData()
        init()
    }

    private fun getData() {
        Log.d("test", Endpoints.getAddressByID(sessionManager.getOnlineUserID()))
        val request =
            StringRequest(Request.Method.GET,
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
        Volley.newRequestQueue(this).add(request)


    }

    private fun init() {
        setupToolBar()
        add_new_address_button.setOnClickListener {
            startActivity(Intent(applicationContext, AddAddressActivity::class.java))
        }
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

}