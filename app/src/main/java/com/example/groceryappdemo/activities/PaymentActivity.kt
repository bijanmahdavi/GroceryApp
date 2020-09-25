package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryappdemo.R
import com.example.groceryappdemo.adapters.PaymentAdapter
import com.example.groceryappdemo.database.DBHelper
import com.example.groceryappdemo.models.Item
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper

    var mList: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        dbHelper = DBHelper(this)
        init()
    }

    private fun getItemData() : ArrayList<Item> {
        return dbHelper.getItems()
    }

    private fun init() {
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
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
            builder.setNegativeButton("No") {
                dialog, p1 ->
                dialog?.dismiss()
            }

            var alertDialog = builder.create()
            alertDialog.show()
        }
    }
    private fun sendOrder() {
        var params = HashMap<String , String>()
        var gson = Gson()
        var obj = gson.toJson(mList)
        Log.d("mList", obj.toString())
        //params["firstName"] = mList.
        //params["password"] = password
        //params["mobile"] = "12345"
        //params["email"] = email

        var jsonObject = JSONObject(params as Map<*, *>)
        Log.d("jsonOBJ", jsonObject.toString())
        /*var request = JsonObjectRequest(
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

        Volley.newRequestQueue(this).add(request)*/
    }
}