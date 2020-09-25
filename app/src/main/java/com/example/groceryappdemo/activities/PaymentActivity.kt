package com.example.groceryappdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.groceryappdemo.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init() {
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
}