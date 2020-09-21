package com.example.groceryappdemo.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {

        val email = intent.getSerializableExtra("email")
        val password = intent.getSerializableExtra("pass")
        //email_edit_text.setText(email.toString())
        //password_edit_text.setText(password.toString())

        submit_button_to_home.setOnClickListener{
            var loginEmail = email_edit_text.text.toString()
            var loginPassword = password_edit_text.text.toString()

            var params = HashMap<String, String>()
            params["email"] = loginEmail
            params["password"] = loginPassword

            var jsonObject = JSONObject(params as Map<*,*>)
            var url = "https://grocery-second-app.herokuapp.com/api/auth/login"
            var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                {
                    Toast.makeText(this,"Login Success!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                },
                {
                    Log.d("dbg", it.toString())
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                })
            Volley.newRequestQueue(this).add(request)
        }

        to_register_screen_text_button.setOnClickListener{

            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}