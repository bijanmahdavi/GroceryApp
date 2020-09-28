package com.example.groceryappdemo.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager {
    var sharedPreferences: SharedPreferences
    private var FILE = "loginPreferences"
    private var ID = "id"
    private var ONLINE = "online"
    private var EMAIL = "email"
    private var NAME = "firstName"
    private var STREETNAME = "streetName"
    private var HOUSENUMBER = "houseNo"
    private var CITY = "city"
    private var PINCODE = "pinCode"

    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
    }

    fun storeID(id: String) {
        sharedPreferences.edit().putString(ID, id).apply()
        sharedPreferences.edit().putBoolean(ONLINE, true).apply()
    }
    fun storeDefaultAddress(streetName:String, houseNumber:Int, city: String, pinCode: Int) {
        sharedPreferences.edit().putString(STREETNAME, streetName).apply()
        sharedPreferences.edit().putInt(HOUSENUMBER, houseNumber).apply()
        sharedPreferences.edit().putString(CITY, city).apply()
        sharedPreferences.edit().putInt(PINCODE, pinCode).apply()
    }

    fun storeEmail(email: String) {
        sharedPreferences.edit().putString(EMAIL, email).apply()
    }

    fun storeFirstName(name: String) {
        sharedPreferences.edit().putString(NAME, name).apply()
    }

    fun getUserOnline(): Boolean {
        return sharedPreferences.getBoolean(ONLINE, false)
    }

    fun getOnlineUserID(): String {
        val user = sharedPreferences.getString(ID, null)
        return user.toString()
    }

    fun getUserEmail(): String {
        val email = sharedPreferences.getString(EMAIL, null)
        return email.toString()
    }

    fun getUserFirstName(): String {
        val firstName = sharedPreferences.getString(NAME, null)
        return firstName.toString()
    }

    fun getDefaultStreet(): String {
        val street = sharedPreferences.getString(STREETNAME, null)
        return street.toString()
    }

    fun getDefaultZip(): Int {
        val pin = sharedPreferences.getInt(PINCODE, 12345)
        return pin
    }


    fun logout() {
        sharedPreferences.edit().putString(ID, "").apply()
        sharedPreferences.edit().putBoolean(ONLINE, false).apply()
    }
}