package com.example.typeracer.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.typeracer.R
import com.example.typeracer.fragment.HistoryFragment
import com.example.typeracer.fragment.ProfileFragment
import com.example.typeracer.fragment.TypeFragment
import com.example.typeracer.util.NavigationHelper as Fragments
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fragments.addFragment(this, R.id.fragment_container, TypeFragment())

        navigation.setOnNavigationItemSelectedListener(getItemSelectedListener())
    }

    private fun getItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            if (it.itemId != navigation.selectedItemId) {
                Fragments.replaceFragment(this, R.id.fragment_container, when (it.itemId) {
                        R.id.navigation_type_race -> TypeFragment()
                        R.id.navigation_history -> HistoryFragment()
                        R.id.navigation_profile -> ProfileFragment()
                        else -> return@OnNavigationItemSelectedListener false
                    }
                )
            }
            return@OnNavigationItemSelectedListener true
        }
    }

}
