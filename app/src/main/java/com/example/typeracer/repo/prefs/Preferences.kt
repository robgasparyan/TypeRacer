package com.example.typeracer.repo.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.typeracer.util.Constants


object Preferences {

    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun putString(KEY_NAME: String, value: String?) {
        val editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getString(KEY_NAME: String, default: String?): String? {
        return sharedPref.getString(KEY_NAME, default)
    }
}