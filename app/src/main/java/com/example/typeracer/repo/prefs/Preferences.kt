package com.example.typeracer.repo.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.typeracer.TypeRacerApplication
import com.example.typeracer.util.PrefConstants


object Preferences {

    private var sharedPref: SharedPreferences

    init {
        val context = TypeRacerApplication.appContext
        sharedPref = context.getSharedPreferences(PrefConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
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