package com.example.groceryappdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.SessionManager

class StartActivity : AppCompatActivity() {

    //2000 milliseconds = 2 seconds
    private var delayedTime: Long = 2000
    //var sessionManager = SessionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var handler = Handler()
        handler.postDelayed({
            /*if(sessionManager.getUserOnline()) {
                //is logged in
            } else {
                //not logged in
            }*/
            startActivity(Intent(this, MainActivity::class.java))

        }, delayedTime)
    }

}