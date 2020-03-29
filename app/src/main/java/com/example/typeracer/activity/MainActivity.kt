package com.example.typeracer.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.typeracer.R
import com.example.typeracer.fragment.HistoryFragment
import com.example.typeracer.fragment.TypeFragment
import com.example.typeracer.util.FragmentHelper as Fragments
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fragments.addFragment(this, R.id.fragment_container, TypeFragment())

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(getItemSelectedListener())
    }

    private fun getItemSelectedListener() = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_type_race -> {
                Fragments.replaceFragment(this, R.id.fragment_container, TypeFragment())
            }
            R.id.navigation_history -> {
                Fragments.replaceFragment(this, R.id.fragment_container, HistoryFragment())
            }
            else -> return@OnNavigationItemSelectedListener false
        }
        return@OnNavigationItemSelectedListener true
    }

}
