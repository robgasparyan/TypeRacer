package com.example.typeracer.util

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object FragmentHelper {

    private fun getFragmentManager(activity: Activity): FragmentManager {
        return (activity as FragmentActivity).supportFragmentManager
    }

    fun addFragment(activity: Activity, @IdRes frameId: Int, fragment: Fragment) {
        val manager = getFragmentManager(activity)
        manager.beginTransaction()
            .add(frameId, fragment, fragment.javaClass.toString())
            .commitAllowingStateLoss()
        manager.executePendingTransactions()
    }

    fun replaceFragment(activity: Activity, @IdRes frameId: Int, fragment: Fragment) {
        val manager = getFragmentManager(activity)
        manager.beginTransaction()
            .replace(frameId, fragment, fragment.javaClass.toString())
            .addToBackStack(null)
            .commitAllowingStateLoss()
        manager.executePendingTransactions()
    }

}