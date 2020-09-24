package com.example.groceryappdemo.app

import android.content.Context
import android.content.SharedPreferences

class SessionManager {
    var sharedPreferences: SharedPreferences
    private var FILE = "loginPreferences"
    private var ID = "id"
    private var ONLINE = "online"

    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
    }

    fun storeID(id: String) {
        sharedPreferences.edit().putString(ID, id).apply()
        sharedPreferences.edit().putBoolean(ONLINE, true).apply()
    }

    fun getUserOnline(): Boolean {
        return sharedPreferences.getBoolean(ONLINE, false)
    }

    fun getOnlineUserID(): String {
        val user = sharedPreferences.getString(ID, null)
        return user.toString()
    }

    fun logout() {
        sharedPreferences.edit().putString(ID, "").apply()
        sharedPreferences.edit().putBoolean(ONLINE, false).apply()
    }
}