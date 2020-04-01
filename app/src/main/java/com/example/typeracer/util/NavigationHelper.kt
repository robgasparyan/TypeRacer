package com.example.typeracer.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object NavigationHelper {

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

    fun openActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

}