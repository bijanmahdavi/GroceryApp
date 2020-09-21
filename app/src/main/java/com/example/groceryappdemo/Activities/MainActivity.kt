package com.example.groceryappdemo.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryappdemo.Fragments.FirstSubCategoryFragment
import com.example.groceryappdemo.R
import com.example.groceryappdemo.app.Endpoints
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), FirstSubCategoryFragment.OnFragmentInteraction {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
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
                        Log.d("dbg_error", it.toString())
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

    override fun onImageClicked(name: ImageView?) {
        Log.d("Working", "Its working!")
        name?.setOnClickListener{

            startActivity(Intent(this, ProductDetailActivity::class.java))
        }



    }
}